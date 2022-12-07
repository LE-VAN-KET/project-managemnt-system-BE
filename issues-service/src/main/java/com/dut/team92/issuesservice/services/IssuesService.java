package com.dut.team92.issuesservice.services;

import com.dut.team92.issuesservice.domain.dto.IssuesDto;
import com.dut.team92.issuesservice.domain.dto.SprintDto;
import com.dut.team92.issuesservice.domain.dto.request.CreateIssuesBacklogCommand;
import com.dut.team92.issuesservice.domain.dto.request.MoveIssuesCommand;
import com.dut.team92.issuesservice.domain.dto.response.MoveIssuesResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface IssuesService {
    IssuesDto createIssues(CreateIssuesBacklogCommand command);
    IssuesDto get(UUID issuesId);
    List<IssuesDto> getAllIssuesBacklogByProjectId(UUID projectId);
    IssuesDto updateIssues(CreateIssuesBacklogCommand command, UUID issuesId);
    void deleteIssues(UUID issuesId);
    List<IssuesDto> getAllIssuesByBoardIdIn(List<UUID> boardIs);
    List<IssuesDto> getAllIssuesInProject(UUID projectId);
    MoveIssuesResponse moveIssues(MoveIssuesCommand command);
    List<SprintDto> getAllIssuesInBoardOfSprintStatusRunningByProjectId(UUID projectId);
}
