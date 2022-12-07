package com.dut.team92.issuesservice.services;

import com.dut.team92.issuesservice.domain.dto.BacklogDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.UUID;

public interface BacklogService {
    BacklogDto getAllIssuesInBacklogAndInSprintStartingOrUnStart(UUID projectId);
}
