package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.ProjectDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateProjectCommand;
import com.dut.team92.organizationservice.domain.dto.request.UpdateProjectCommand;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    List<ProjectDto> getAllProjectByOrganizationId(UUID organizationId);
    ProjectDto createProjectForOrganization(UUID organizationId, CreateProjectCommand command);
    ProjectDto getOneProjectById(UUID projectId, UUID organizationId);
    ProjectDto editProject(UUID organizationId, UpdateProjectCommand command);
    void removeProject(UUID organizationId, UUID projectId);
    boolean isExistProjectByProjectIdAndOrganizationId(UUID projectId, UUID organizationId);
    String getProjectKeyByProjectId(UUID projectId);
    List<ProjectDto> getAllProjectAttendingInOrganizationId(UUID organizationId);
}
