package com.dut.team92.issuesservice.services;

import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.issuesservice.domain.dto.BacklogDto;
import com.dut.team92.issuesservice.domain.dto.IssuesDto;
import com.dut.team92.issuesservice.domain.dto.SprintDto;
import com.dut.team92.issuesservice.exception.ProjectNotFoundException;
import com.dut.team92.issuesservice.proxy.OrganizationServiceProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BacklogServiceImpl implements BacklogService{
    private final IssuesService issuesService;
    private final OrganizationServiceProxy organizationServiceProxy;
    private final HttpServletRequest request;

    @Override
    @Transactional(readOnly = true)
    public BacklogDto getAllIssuesInBacklogAndInSprintStartingOrUnStart(UUID projectId) throws JsonProcessingException {
        List<IssuesDto> issues = issuesService.getAllIssuesBacklogByProjectId(projectId);
        Object response = organizationServiceProxy.getAllSprintStaringOrUnStart(projectId.toString(),
                request.getHeader(HttpHeaders.AUTHORIZATION));
        ObjectMapper mapper = new ObjectMapper();
        if (response instanceof List) {
            List<SprintDto> sprintDtos = mapper.readValue(mapper.writeValueAsString(response),
                    new TypeReference<List<SprintDto>>(){});
            List<UUID> boardIds = new ArrayList<>();
            Map<UUID, SprintDto> sprintDtoMap = new HashMap<>();
            Map<UUID, UUID> boardInSprintMap = new HashMap<>();
            sprintDtos.forEach(sprint -> {
                boardIds.addAll(sprint.getBoardIds());
                sprintDtoMap.put(sprint.getId(), sprint);
                sprint.getBoardIds().forEach(id -> boardInSprintMap.put(id, sprint.getId()));
            });

            issuesService.getAllIssuesByBoardIdIn(boardIds).forEach(issuesDto -> {
                UUID sprintId = boardInSprintMap.get(issuesDto.getBoardId());
                sprintDtoMap.get(sprintId).getIssuesList().add(issuesDto);
            });

            return BacklogDto.builder().sprints(new ArrayList<>(sprintDtoMap.values())).backlog(issues).build();
        } else {
            CommonErrorResponse error = mapper.readValue(mapper.writeValueAsString(response),
                    CommonErrorResponse.class);
            throw new ProjectNotFoundException(error.getMessage());
        }
    }
}
