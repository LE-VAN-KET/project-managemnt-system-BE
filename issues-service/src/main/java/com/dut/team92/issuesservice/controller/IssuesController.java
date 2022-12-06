package com.dut.team92.issuesservice.controller;

import com.dut.team92.issuesservice.domain.dto.IssuesDto;
import com.dut.team92.issuesservice.domain.dto.request.CreateIssuesBacklogCommand;
import com.dut.team92.issuesservice.services.IssuesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/issues")
@SecurityRequirement(name = "BearerAuth")
public class IssuesController {

    @Autowired
    private IssuesService issuesService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public IssuesDto createIssues(@Valid @RequestBody CreateIssuesBacklogCommand command) {
        return issuesService.createIssues(command);
    }

    @GetMapping("/{issues_id}")
    @ResponseStatus(HttpStatus.OK)
    public IssuesDto getIssues(@PathVariable("issues_id") @NotNull String issuesId) {
        return issuesService.get(UUID.fromString(issuesId));
    }

    @GetMapping("/backlog")
    @ResponseStatus(HttpStatus.OK)
    public List<IssuesDto> getAllIssuesBacklogByProjectId(@RequestParam(name = "project_id") String projectId) {
        return issuesService.getAllIssuesBacklogByProjectId(UUID.fromString(projectId));
    }

    @PutMapping("/{issues_id}")
    @ResponseStatus(HttpStatus.OK)
    public IssuesDto updateDetailsIssues(@PathVariable("issues_id") String issuesId,
                                         @RequestBody CreateIssuesBacklogCommand command) {
        return issuesService.updateIssues(command, UUID.fromString(issuesId));
    }

    @DeleteMapping("/{issues_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteIssues(@PathVariable("issues_id") String issuesId) {
        issuesService.deleteIssues(UUID.fromString(issuesId));
    }

    @GetMapping
    public List<IssuesDto> getAllIssuesInProject(@RequestParam("project_id") String projectId) {
        return issuesService.getAllIssuesInProject(UUID.fromString(projectId));
    }

    @PostMapping("/")
    public void moveIssuesBacklogToBacklog() {

    }

}
