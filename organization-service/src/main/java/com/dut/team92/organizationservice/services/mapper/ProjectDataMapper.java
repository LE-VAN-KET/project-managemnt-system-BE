package com.dut.team92.organizationservice.services.mapper;

import com.dut.team92.common.domain.mapper.BaseMapper;
import com.dut.team92.organizationservice.domain.dto.ProjectDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateProjectCommand;
import com.dut.team92.organizationservice.domain.entity.Project;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProjectDataMapper extends BaseMapper<Project, ProjectDto> {
    @Override
    public Project convertToEntity(ProjectDto dto, Object... args) {
        Project entity = new Project();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    @Override
    public ProjectDto convertToDto(Project entity, Object... args) {
        ProjectDto dto = new ProjectDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
            if (entity.getParent() != null) {
                ProjectDto parentDto = new ProjectDto();
                BeanUtils.copyProperties(entity.getParent(), parentDto);
                dto.setParent(parentDto);
            }
        }
        return dto;
    }

    public Project convertCreateProjectCommandToEntity(CreateProjectCommand command, UUID organizationId) {
        Project project = new Project();
        if (command != null && organizationId != null) {
            BeanUtils.copyProperties(command, project);
            project.setOrganizationId(organizationId);
        }

        return project;
    }
}
