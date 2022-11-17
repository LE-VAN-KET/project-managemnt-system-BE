package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.ProjectDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateProjectCommand;
import com.dut.team92.organizationservice.domain.dto.request.UpdateProjectCommand;
import com.dut.team92.organizationservice.domain.entity.Project;
import com.dut.team92.organizationservice.exception.ProjectIdNotFound;
import com.dut.team92.organizationservice.repository.ProjectRepository;
import com.dut.team92.organizationservice.services.handler.CreateProjectCommandHandler;
import com.dut.team92.organizationservice.services.handler.UpdateProjectCommandHandler;
import com.dut.team92.organizationservice.services.mapper.ProjectDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final ProjectDataMapper projectDataMapper;
    private final CreateProjectCommandHandler createProjectCommandHandler;
    private final UpdateProjectCommandHandler updateProjectCommandHandler;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDto> getAllProjectByOrganizationId(UUID organizationId) {
        List<Project> projects = projectRepository.findAllByOrganizationId(organizationId);
        return projects.isEmpty() ? Collections.emptyList()
                : projectDataMapper.convertToDtoList(projects);
    }

    @Override
    public ProjectDto createProjectForOrganization(UUID organizationId, CreateProjectCommand command) {
        Project createdProject = createProjectCommandHandler.createProjectCommandHandler(organizationId,
                command);
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
}
