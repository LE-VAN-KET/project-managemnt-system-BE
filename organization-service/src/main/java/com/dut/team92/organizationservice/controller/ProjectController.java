package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.domain.dto.request.CreateProjectCommand;
import com.dut.team92.organizationservice.domain.dto.request.UpdateProjectCommand;
import com.dut.team92.organizationservice.domain.dto.response.CheckProjectExistResponse;
import com.dut.team92.organizationservice.domain.dto.response.ProjectKeyResponse;
import com.dut.team92.organizationservice.domain.dto.response.Response;
import com.dut.team92.organizationservice.proxy.MemberServiceProxy;
import com.dut.team92.organizationservice.services.BoardService;
import com.dut.team92.organizationservice.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/organizations/{organization_id}/projects")
@Slf4j
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberServiceProxy memberServiceProxy;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object getAllProject(@PathVariable("organization_id") @NotNull String organizationId,
                                @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_PROJECTS" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return projectService.getAllProjectByOrganizationId(UUID.fromString(organizationId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object createProject(@PathVariable("organization_id") @NotNull String organizationId,
                                    @RequestBody CreateProjectCommand command,
                                @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("ADD_PROJECT" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return projectService.createProjectForOrganization(UUID.fromString(organizationId),
                command);
    }

    @GetMapping("/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getOneProject(@PathVariable("organization_id") @NotNull String organizationId,
                                    @PathVariable("project_id") @NotNull String projectId,
                                @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_DETAIL_PROJECT" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return projectService.getOneProjectById(UUID.fromString(projectId), UUID.fromString(organizationId));
    }

    @PutMapping("/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object editProject(@PathVariable("organization_id") @NotNull String organizationId,
                                    @PathVariable("project_id") @NotNull String projectId,
                                    @Valid @RequestBody UpdateProjectCommand command,
                              @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                              @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("UPDATE_PROJECT" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        command.setId(UUID.fromString(projectId));
        return projectService.editProject(UUID.fromString(organizationId), command);
    }

    @DeleteMapping("/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object deleteProject(@PathVariable("organization_id") @NotNull String organizationId,
                                    @PathVariable("project_id") @NotNull String projectId,
                                @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("DELETE_PROJECT" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        projectService.removeProject(UUID.fromString(organizationId), UUID.fromString(projectId));
        return null;
    }

    @GetMapping("/check/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object existProjectByIdAndOrganizationId(@PathVariable("organization_id") @NotNull String organizationId,
                                                                       @PathVariable("project_id") @NotNull String projectId,
                                                    @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                                    @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("CHECK_EXIST_PROJECT" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        boolean existProject = projectService.isExistProjectByProjectIdAndOrganizationId(
                UUID.fromString(projectId),
                UUID.fromString(organizationId));
        return CheckProjectExistResponse.builder().isExistProject(existProject).build();
    }

    @GetMapping("/{project_id}/project-key")
    @ResponseStatus(HttpStatus.OK)
    public Object getProjectKey(@PathVariable("project_id") String projectKey,
                                            @PathVariable String organization_id,
                                @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_PROJECT_KEY" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return ProjectKeyResponse.builder().key(
                projectService.getProjectKeyByProjectId(UUID.fromString(projectKey))
        ).build();
    }

    @GetMapping("/attending")
    @ResponseStatus(HttpStatus.OK)
    public Object getAllProjectAttended(@PathVariable("organization_id") @NotNull String organizationId,
                                        @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                        @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_ALL_PROJECT_ATTENDED" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return projectService.getAllProjectAttendingInOrganizationId(UUID.fromString(organizationId));
    }

}
