package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.domain.dto.ProjectDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateProjectCommand;
import com.dut.team92.organizationservice.domain.dto.request.UpdateProjectCommand;
import com.dut.team92.organizationservice.domain.dto.response.CheckProjectExistResponse;
import com.dut.team92.organizationservice.domain.dto.response.ProjectKeyResponse;
import com.dut.team92.organizationservice.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organizations/{organization_id}/projects")
@Slf4j
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> getAllProject(@PathVariable("organization_id") @NotNull String organizationId) {
        return projectService.getAllProjectByOrganizationId(UUID.fromString(organizationId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@PathVariable("organization_id") @NotNull String organizationId,
                                    @RequestBody CreateProjectCommand command) {
        return projectService.createProjectForOrganization(UUID.fromString(organizationId),
                command);
    }

    @GetMapping("/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto getOneProject(@PathVariable("organization_id") @NotNull String organizationId,
                                    @PathVariable("project_id") @NotNull String projectId) {
        return projectService.getOneProjectById(UUID.fromString(projectId), UUID.fromString(organizationId));
    }

    @PutMapping("/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto editProject(@PathVariable("organization_id") @NotNull String organizationId,
                                    @PathVariable("project_id") @NotNull String projectId,
                                    @Valid @RequestBody UpdateProjectCommand command) {
        command.setId(UUID.fromString(projectId));
        return projectService.editProject(UUID.fromString(organizationId), command);
    }

    @DeleteMapping("/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@PathVariable("organization_id") @NotNull String organizationId,
                                    @PathVariable("project_id") @NotNull String projectId) {
        projectService.removeProject(UUID.fromString(organizationId), UUID.fromString(projectId));
    }

    @GetMapping("/check/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public CheckProjectExistResponse existProjectByIdAndOrganizationId(@PathVariable("organization_id") @NotNull String organizationId,
                                                                       @PathVariable("project_id") @NotNull String projectId) {
        boolean existProject = projectService.isExistProjectByProjectIdAndOrganizationId(
                UUID.fromString(projectId),
                UUID.fromString(organizationId));
        return CheckProjectExistResponse.builder().isExistProject(existProject).build();
    }

    @GetMapping("/{project_id}/project-key")
    @ResponseStatus(HttpStatus.OK)
    public ProjectKeyResponse getProjectKey(@PathVariable("project_id") String projectKey,
                                            @PathVariable String organization_id) {
        return ProjectKeyResponse.builder().key(
                projectService.getProjectKeyByProjectId(UUID.fromString(projectKey))
        ).build();
    }

    @GetMapping("/attending")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> getAllProjectAttended(@PathVariable("organization_id") @NotNull String organizationId) {
        return projectService.getAllProjectAttendingInOrganizationId(UUID.fromString(organizationId));
    }

}
