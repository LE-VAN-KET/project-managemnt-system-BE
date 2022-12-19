package com.dut.team92.organizationservice.services.handler;

import com.dut.team92.common.enums.ProjectStatus;
import com.dut.team92.organizationservice.domain.dto.request.CreateProjectCommand;
import com.dut.team92.organizationservice.domain.entity.Project;
import com.dut.team92.organizationservice.exception.OrganizationIdNotFound;
import com.dut.team92.organizationservice.exception.ProjectDomainExisted;
import com.dut.team92.organizationservice.exception.ProjectIdNotFound;
import com.dut.team92.organizationservice.repository.ProjectRepository;
import com.dut.team92.organizationservice.services.OrganizationService;
import com.dut.team92.organizationservice.services.mapper.ProjectDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateProjectCommandHandler {

    private final ProjectRepository projectRepository;
    private final OrganizationService organizationService;
    private final ProjectDataMapper projectDataMapper;

    public Project createProjectCommandHandler(UUID organizationId, CreateProjectCommand command) {
        boolean existOrganization = organizationService.isExistOrganizationById(organizationId);
        if (!existOrganization) {
            throw new OrganizationIdNotFound(400, "Organization not fount with Id = " + organizationId);
        }

        Project project = projectDataMapper.convertCreateProjectCommandToEntity(command, organizationId);

        if (command.getParentId() != null) {
            Project projectParent = projectRepository.findById(command.getParentId())
                    .orElseThrow(() -> new ProjectIdNotFound("Project not found with id = " + command.getParentId()));

            project.setParent(projectParent);
        }
        validateProjectCommand(command);
        project.setProjectStatus(ProjectStatus.ACTIVE);
        return projectRepository.saveAndFlush(project);
    }

    private void validateProjectCommand(CreateProjectCommand command) {
        if (projectRepository.existsByDomain(command.getDomain())) {
            throw new ProjectDomainExisted(400, "Project Domain already exists!");
        }
    }
}
