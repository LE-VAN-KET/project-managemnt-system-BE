package com.dut.team92.issuesservice.services;

import com.dut.team92.issuesservice.domain.dto.BacklogDto;
import com.dut.team92.issuesservice.domain.dto.BoardDto;
import com.dut.team92.issuesservice.domain.dto.IssuesDto;
import com.dut.team92.issuesservice.domain.dto.SprintDto;
import com.dut.team92.issuesservice.proxy.OrganizationServiceProxy;
import com.dut.team92.issuesservice.services.mapper.ObjectDataMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BacklogServiceImpl implements BacklogService{
    private final IssuesService issuesService;
    private final OrganizationServiceProxy organizationServiceProxy;
    private final HttpServletRequest request;
    private final ObjectDataMapper objectDataMapper;

    @Override
    @Transactional(readOnly = true)
    public BacklogDto getAllIssuesInBacklogAndInSprintStartingOrUnStart(UUID projectId) {
        List<IssuesDto> issues = issuesService.getAllIssuesBacklogByProjectId(projectId);
        Object response = organizationServiceProxy.getAllSprintStaringOrUnStart(projectId.toString(),
                request.getHeader(HttpHeaders.AUTHORIZATION));
        List<SprintDto> sprintDtos = objectDataMapper.convertObjectToSprintDto(response);
        List<UUID> boardIds = new ArrayList<>();
        Map<UUID, SprintDto> sprintDtoMap = new HashMap<>();
        Map<UUID, UUID> boardInSprintMap = new HashMap<>();
        sprintDtos.forEach(sprint -> {
            List<UUID> boardIdListInSprint = sprint.getBoardDtoList().stream().map(BoardDto::getId)
                    .collect(Collectors.toList());
            boardIds.addAll(boardIdListInSprint);
            sprintDtoMap.put(sprint.getId(), sprint);
            sprint.setBoardIds(boardIdListInSprint);
            sprint.getBoardDtoList().forEach(board -> boardInSprintMap.put(board.getId(), sprint.getId()));
        });

        issuesService.getAllIssuesByBoardIdIn(boardIds).forEach(issuesDto -> {
            UUID sprintId = boardInSprintMap.get(issuesDto.getBoardId());
            List<IssuesDto> issuesDtoList = sprintDtoMap.get(sprintId).getIssuesList();
            if (Objects.isNull(issuesDtoList)) {
                sprintDtoMap.get(sprintId).setIssuesList(new ArrayList<>());
            }
            sprintDtoMap.get(sprintId).getIssuesList().add(issuesDto);
        });

        return BacklogDto.builder().sprints(new ArrayList<>(sprintDtoMap.values())).backlog(issues).build();
    }
}
