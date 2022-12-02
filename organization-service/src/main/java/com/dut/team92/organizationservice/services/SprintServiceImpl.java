package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.MoveIssuesType;
import com.dut.team92.organizationservice.domain.dto.SprintDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;
import com.dut.team92.organizationservice.domain.entity.Board;
import com.dut.team92.organizationservice.domain.entity.Sprint;
import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import com.dut.team92.organizationservice.domain.entity.SprintWithBoard;
import com.dut.team92.organizationservice.exception.ProjectIdNotFound;
import com.dut.team92.organizationservice.exception.SprintNotFoundException;
import com.dut.team92.organizationservice.repository.BoardRepository;
import com.dut.team92.organizationservice.repository.ProjectRepository;
import com.dut.team92.organizationservice.repository.SprintRepository;
import com.dut.team92.organizationservice.services.mapper.SprintDataMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public SprintServiceImpl(SprintRepository sprintRepository,
                             SprintDataMapper sprintDataMapper,
                             ProjectRepository projectRepository,
                             BoardService boardService,
                             BoardRepository boardRepository) {
        this.sprintRepository = sprintRepository;
        this.sprintDataMapper = sprintDataMapper;
        this.projectRepository = projectRepository;
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    @CacheEvict(cacheNames = "sprints", allEntries = true)
    @Override
    public SprintDto createSprint(CreateSprintCommand command) {
        validateCreateSprint(command);
        Sprint sprint = sprintDataMapper.createSprintCommandToSprint(command);
        sprint.setIsDelete(false);
        sprint.setName(getKeyProject(sprint.getProjectId()) + " " + sprint.getName());
        Sprint savedSprint = sprintRepository.save(sprint);
        // create default 3 board to sprint
        boardService.createDefaultBoard(savedSprint.getId());
        return sprintDataMapper.convertToDto(savedSprint);
    }

    @CacheEvict(cacheNames = "sprints", key = "#dto.id")
    @Override
    public SprintDto updateSprint(SprintDto dto) {
        Sprint existSprint = getOneSprint(dto.getId());
        BeanUtils.copyProperties(dto, existSprint);
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

        long countSprintInProject = sprintRepository.countByProjectId(existSprint.getProjectId());
        // create new sprint unstart
        Sprint sprint = createSprintAuto(projectKey, countSprintInProject, existSprint.getProjectId());
        switch (moveIssuesType) {
            case NEW_SPRINT:

                // mai doing
        }
    }

    @Cacheable(cacheNames = "sprintsByStatusStartingOrUnStart", key = "#projectId")
    @Override
    public List<SprintDto> getAllSprintStartingOrUnStart(UUID projectId) {
        List<SprintWithBoard> sprintWithBoards = boardRepository.findAllBySprintStatusInAndProjectId(
                Arrays.asList(SprintStatus.UNSTART, SprintStatus.STARTING), projectId);
        Map<UUID, Sprint> sprintMap = new HashMap<>();
        sprintWithBoards.forEach(sprintWithBoard -> {
            UUID sprintId = sprintWithBoard.getSprint().getId();
            if (!sprintMap.containsKey(sprintId)) {
                sprintMap.put(sprintId, sprintWithBoard.getSprint());
            }
            Sprint sprint = sprintMap.get(sprintId);
            sprint.getBoardIds().add(sprintWithBoard.getBoardId());
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
    public Sprint createSprintAuto(String projectKey, long countSprintInProject, UUID projectId) {
        Sprint sprint = new Sprint();
        sprint.setStatus(SprintStatus.UNSTART);
        sprint.setName(StringUtils.join(projectKey, " Sprint ", countSprintInProject + 1));
        sprint.setProjectId(projectId);
        sprint.setIsDelete(false);
        sprint.setPosition(sprintRepository.maxPosition() + 1);
        return sprintRepository.save(sprint); // save sprint
    }

    @Cacheable(cacheNames = "keyProject", key = "#projectId")
    public String getKeyProject(UUID projectId) {
        return projectRepository.findProjectKeyByProjectId(projectId)
                .orElseThrow(() -> new ProjectIdNotFound("Project not found with id = "
                        + projectId));
    }
}
