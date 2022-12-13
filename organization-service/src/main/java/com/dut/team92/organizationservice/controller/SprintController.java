package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.domain.dto.MoveIssuesType;
import com.dut.team92.organizationservice.domain.dto.SprintDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;
import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import com.dut.team92.organizationservice.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {
    @Autowired
    private SprintService sprintService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SprintDto createSprint(@Valid @RequestBody CreateSprintCommand command) {
        return sprintService.createSprint(command);
    }

    @PutMapping("/{sprint_id}")
    @ResponseStatus(HttpStatus.OK)
    public SprintDto updateSprint(@Valid @RequestBody SprintDto sprintDto,
                                  @PathVariable("sprint_id") String sprintId) {
        sprintDto.setId(UUID.fromString(sprintId));
        return sprintService.updateSprint(sprintDto);
    }

    @PutMapping("/{sprint_id}/complete")
    public void completeSprint(@PathVariable("sprint_id") String sprintId,
                               @RequestParam MoveIssuesType moveIssuesType) {
        sprintService.completedSprint(UUID.fromString(sprintId), moveIssuesType);
    }

    @GetMapping("/backlog")
    public List<SprintDto> getAllSprintStaringOrUnStart(@RequestParam(name = "project_id")
                                                            String projectId) {
        return sprintService.getAllSprintByListStatus(UUID.fromString(projectId),
                SprintStatus.UNSTART, SprintStatus.STARTING);
    }

    @GetMapping("/boards")
    public List<SprintDto> getAllSprintRunning(@RequestParam(name = "project_id")
                                                        String projectId) {
        return sprintService.getAllSprintByListStatus(UUID.fromString(projectId),
                SprintStatus.STARTING);
    }

}
