package com.dut.team92.issuesservice.controller;

import com.dut.team92.issuesservice.domain.dto.BacklogDto;
import com.dut.team92.issuesservice.services.BacklogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/backlogs")
public class BacklogController {
    @Autowired
    private BacklogService backlogService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BacklogDto getBacklog(@RequestParam(name = "project_id") String projectId) throws JsonProcessingException {
        return backlogService.getAllIssuesInBacklogAndInSprintStartingOrUnStart(UUID.fromString(projectId));
    }
}
