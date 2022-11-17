package com.dut.team92.organizationservice.services.handler;

import com.dut.team92.organizationservice.domain.dto.request.UpdateProjectCommand;
import com.dut.team92.organizationservice.domain.entity.Project;
import com.dut.team92.organizationservice.exception.ProjectDomainExisted;
import com.dut.team92.organizationservice.exception.ProjectIdNotFound;
import com.dut.team92.organizationservice.repository.ProjectRepository;
import com.dut.team92.organizationservice.services.mapper.ProjectDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateProjectCommandHandler {
    private final ProjectDataMapper projectDataMapper;
    private final ProjectRepository projectRepository;

    public Project updateProjectCommandHandle(UUID organizationId, UpdateProjectCommand command) {
        Project existProject = projectRepository.findByIdAndOrganizationId(command.getId(), organizationId)
                .orElseThrow(() -> new ProjectIdNotFound("Project not found with Id = " + command.getId()));
        // validate new domain if exist then throw exception
        validateUpdateProjectCommand(command, existProject.getDomain());
        BeanUtils.copyProperties(command, existProject, "id");
        return projectRepository.save(existProject);
    }

    private void validateUpdateProjectCommand(UpdateProjectCommand command, String oldDomain) {
        if (!oldDomain.equals(command.getDomain())) {
            boolean isExistDomain = projectRepository.existsByDomain(command.getDomain());
            if (isExistDomain) {
                throw new ProjectDomainExisted(400, "Project Domain already exists with "
                        + command.getDomain());
            }
        }
    }
}
