package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.ProjectDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateProjectCommand;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;
import com.dut.team92.organizationservice.domain.dto.request.UpdateProjectCommand;
import com.dut.team92.organizationservice.domain.dto.response.ProjectResponse;
import com.dut.team92.organizationservice.domain.entity.Project;
import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import com.dut.team92.organizationservice.exception.ProjectIdNotFound;
import com.dut.team92.organizationservice.proxy.MemberServiceProxy;
import com.dut.team92.organizationservice.repository.ProjectRepository;
import com.dut.team92.organizationservice.services.handler.CreateProjectCommandHandler;
import com.dut.team92.organizationservice.services.handler.UpdateProjectCommandHandler;
import com.dut.team92.organizationservice.services.mapper.ObjectDataMapper;
import com.dut.team92.organizationservice.services.mapper.ProjectDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final ProjectDataMapper projectDataMapper;
    private final CreateProjectCommandHandler createProjectCommandHandler;
    private final UpdateProjectCommandHandler updateProjectCommandHandler;
    private final SprintService sprintService;
    private final ObjectDataMapper objectDataMapper;
    private final MemberServiceProxy memberServiceProxy;
    private final HttpServletRequest request;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDto> getAllProjectByOrganizationId(UUID organizationId) {
        List<Project> projects = projectRepository.findAllByOrganizationId(organizationId);
        return projects.isEmpty() ? Collections.emptyList()
                : projectDataMapper.convertToDtoList(projects);
    }

    @Override
    @Transactional(rollbackFor = {ProjectIdNotFound.class, Exception.class})
    public ProjectDto createProjectForOrganization(UUID organizationId, CreateProjectCommand command) {
        Project createdProject = createProjectCommandHandler.createProjectCommandHandler(organizationId,
                command);
        // auto create the first sprint
        createFirstSprintAfterCreateProject(createdProject.getId());
        return projectDataMapper.convertToDto(createdProject);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDto getOneProjectById(UUID projectId, UUID organizationId) {
        Project project = projectRepository.findByIdAndOrganizationId(projectId, organizationId).orElseThrow(() ->
                new ProjectIdNotFound("Project not found with Id = " + projectId));
        return projectDataMapper.convertToDto(project);
    }

    @Override
    public ProjectDto editProject(UUID organizationId, UpdateProjectCommand command) {
        Project updatedProject = updateProjectCommandHandler.updateProjectCommandHandle(organizationId, command);
        return projectDataMapper.convertToDto(updatedProject);
    }

    @Override
    public void removeProject(UUID organizationId, UUID projectId) {
        Project existsProject = projectRepository.findById(projectId).orElseThrow(() ->
                new ProjectIdNotFound("Project not found with Id = " + projectId));
        projectRepository.delete(existsProject);
    }

    @Transactional
    @Async("threadPoolTaskExecutor")
    public void createFirstSprintAfterCreateProject(UUID projectId) {
        CreateSprintCommand command = new CreateSprintCommand();
        command.setProjectId(projectId);
        command.setName("Sprint");
        command.setStatus(SprintStatus.UNSTART);
        sprintService.createSprint(command);
    }

    @Override
    public boolean isExistProjectByProjectIdAndOrganizationId(UUID projectId, UUID organizationId) {
        return projectRepository.existsByIdAndOrganizationId(projectId, organizationId);
    }

    @Override
    public String getProjectKeyByProjectId(UUID projectId) {
        return projectRepository.findProjectKeyByProjectId(projectId).orElseThrow(() ->
                new ProjectIdNotFound("Project not found with id = " + projectId));
    }

    @Override
    public List<ProjectDto> getAllProjectAttendingInOrganizationId(UUID organizationId) {
        Object response = memberServiceProxy.getListProjectIdMemberAttending(organizationId.toString(),
                request.getHeader(HttpHeaders.AUTHORIZATION));
        List<ProjectResponse> projectResponseList = objectDataMapper.convertObjectToProjectId(response);
        List<Project> projectList;
        if (projectResponseList.isEmpty()) {
            return Collections.emptyList();
        } else if (projectResponseList.size() == 1 && projectResponseList.get(0).isAdminOrganization()) {
            projectList = projectRepository.findAllByOrganizationId(organizationId);
        } else {
            List<UUID> projectIdList = projectResponseList.stream().map(ProjectResponse::getProjectId)
                    .collect(Collectors.toList());
            projectList = projectRepository.findAllByIdIn(projectIdList);
        }

        return projectList.isEmpty() ? Collections.emptyList() : projectDataMapper.convertToDtoList(projectList);
    }

}
