package com.dut.team92.organizationservice.services;

import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.organizationservice.domain.dto.BoardDto;
import com.dut.team92.organizationservice.domain.dto.MoveIssuesType;
import com.dut.team92.organizationservice.domain.dto.SprintDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;
import com.dut.team92.organizationservice.domain.dto.request.IssuesTransferRequest;
import com.dut.team92.organizationservice.domain.entity.Board;
import com.dut.team92.organizationservice.domain.entity.Sprint;
import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import com.dut.team92.organizationservice.domain.entity.SprintWithBoard;
import com.dut.team92.organizationservice.exception.CompleteSprintException;
import com.dut.team92.organizationservice.exception.ProjectIdNotFound;
import com.dut.team92.organizationservice.exception.SprintNotFoundException;
import com.dut.team92.organizationservice.proxy.IssuesServiceProxy;
import com.dut.team92.organizationservice.repository.BoardRepository;
import com.dut.team92.organizationservice.repository.ProjectRepository;
import com.dut.team92.organizationservice.repository.SprintRepository;
import com.dut.team92.organizationservice.services.mapper.BoardDataMapper;
import com.dut.team92.organizationservice.services.mapper.SprintDataMapper;
import com.dut.team92.organizationservice.utils.PropertyPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "sprintsCache")
public class SprintServiceImpl implements SprintService{
    private final SprintRepository sprintRepository;
    private final SprintDataMapper sprintDataMapper;
    private final ProjectRepository projectRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final IssuesServiceProxy issuesServiceProxy;
    private final HttpServletRequest request;

    @Autowired
    public SprintServiceImpl(SprintRepository sprintRepository,
                             SprintDataMapper sprintDataMapper,
                             ProjectRepository projectRepository,
                             BoardService boardService,
                             BoardRepository boardRepository,
                             IssuesServiceProxy issuesServiceProxy, HttpServletRequest request) {
        this.sprintRepository = sprintRepository;
        this.sprintDataMapper = sprintDataMapper;
        this.projectRepository = projectRepository;
        this.boardService = boardService;
        this.boardRepository = boardRepository;
        this.issuesServiceProxy = issuesServiceProxy;
        this.request = request;
    }

    @CacheEvict(cacheNames = "sprints", allEntries = true)
    @Override
    public SprintDto createSprint(CreateSprintCommand command) {
        validateCreateSprint(command);
        Sprint sprint = sprintDataMapper.createSprintCommandToSprint(command);
        sprint.setIsDelete(false);
        int maxPositionSprintInProject = sprintRepository.maxPositionByProjectId(command.getProjectId());
        int position = maxPositionSprintInProject + 1;
        sprint.setName(getKeyProject(sprint.getProjectId()) + " " + sprint.getName() + " " + position);
        sprint.setPosition(position);
        Sprint savedSprint = sprintRepository.save(sprint);
        // create default 3 board to sprint
        boardService.createDefaultBoard(savedSprint.getId());
        return sprintDataMapper.convertToDto(savedSprint);
    }

    @CacheEvict(cacheNames = "sprints", key = "#dto.id")
    @Override
    public SprintDto updateSprint(SprintDto dto) {
        Sprint existSprint = getOneSprint(dto.getId());
        BeanUtils.copyProperties(dto, existSprint, PropertyPojo.getNullPropertyNames(dto));
        return sprintDataMapper.convertToDto(sprintRepository.save(existSprint));
    }

    @Override
    @Transactional
    public void completedSprint(UUID sprintId, MoveIssuesType moveIssuesType) {
        // update sprint old
        Sprint existSprint = getOneSprint(sprintId);
        existSprint.setStatus(SprintStatus.COMPLETED);
        sprintRepository.save(existSprint);
        String projectKey = getKeyProject(existSprint.getProjectId());

        int maxPositionSprintInProject = sprintRepository.maxPositionByProjectId(existSprint.getProjectId());
        // create new sprint unstart
        Sprint savedSprint = createSprintAuto(projectKey, maxPositionSprintInProject, existSprint.getProjectId());
        // create default 3 board to sprint
        List<Board> newBoardList = boardService.createDefaultBoard(savedSprint.getId());
        List<BoardDto> oldBoardDtoList = boardService.getAllBoardBySprintId(existSprint.getId());
        Object response;
        switch (moveIssuesType) {
            case NEW_SPRINT:
                 response = issuesServiceProxy.transferIssuesToNewSprint(
                         new IssuesTransferRequest(oldBoardDtoList, new BoardDataMapper()
                                 .convertToDtoList(newBoardList)),
                        request.getHeader(HttpHeaders.AUTHORIZATION));
                resolveExceptionResponseProxy(response);
                break;
            case BACKLOG:
                List<UUID> boardIdList = oldBoardDtoList.stream().map(BoardDto::getId).collect(Collectors.toList());
                response = issuesServiceProxy.transferIssuesToBacklog(boardIdList,
                        request.getHeader(HttpHeaders.AUTHORIZATION));
                resolveExceptionResponseProxy(response);
                break;
        }
    }

    @Override
    public List<SprintDto> getAllSprintByListStatus(UUID projectId, SprintStatus... statuses) {
        List<SprintWithBoard> sprintWithBoards = boardRepository.findAllBySprintStatusInAndProjectId(
        Arrays.asList(statuses), projectId);
        Map<UUID, Sprint> sprintMap = new HashMap<>();
        sprintWithBoards.forEach(sprintWithBoard -> {
            UUID sprintId = sprintWithBoard.getSprint().getId();
            if (!sprintMap.containsKey(sprintId)) {
                sprintMap.put(sprintId, sprintWithBoard.getSprint());
            }
            Sprint sprint = sprintMap.get(sprintId);
            sprint.getBoardList().add(sprintWithBoard.getBoard());
        });

        return sprintDataMapper.convertToDtoList(sprintMap.values());
    }

    void validateCreateSprint(CreateSprintCommand command) {
        boolean isExistProject = projectRepository.existsById(command.getProjectId());
        if (!isExistProject) {
            throw new ProjectIdNotFound("Project not found with id: " + command.getProjectId());
        }
    }

    @Cacheable(cacheNames = "sprints", key = "#sprintId")
    public Sprint getOneSprint(UUID sprintId) {
        return sprintRepository.findById(sprintId).orElseThrow(() ->
                new SprintNotFoundException("Sprint not found with id = " + sprintId));
    }

    @CacheEvict(cacheNames = "sprints", allEntries = true)
    public Sprint createSprintAuto(String projectKey, int maxPositionSprintInProject, UUID projectId) {
        Sprint sprint = new Sprint();
        sprint.setStatus(SprintStatus.UNSTART);
        sprint.setName(StringUtils.join(projectKey, " Sprint ", maxPositionSprintInProject + 1));
        sprint.setProjectId(projectId);
        sprint.setIsDelete(false);
        sprint.setPosition(maxPositionSprintInProject + 1);
        return sprintRepository.save(sprint); // save sprint
    }

    @Cacheable(cacheNames = "keyProject", key = "#projectId")
    public String getKeyProject(UUID projectId) {
        return projectRepository.findProjectKeyByProjectId(projectId)
                .orElseThrow(() -> new ProjectIdNotFound("Project not found with id = "
                        + projectId));
    }

    private void resolveExceptionResponseProxy(Object response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (response instanceof CommonErrorResponse) {
                CommonErrorResponse error = mapper.readValue(mapper.writeValueAsString(response),
                        CommonErrorResponse.class);
                throw new CompleteSprintException(error.getMessage());
            }
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
