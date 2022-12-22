package com.dut.team92.organizationservice.services.mapper;

import com.dut.team92.common.domain.mapper.BaseMapper;
import com.dut.team92.organizationservice.domain.dto.OrganizationDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.organizationservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.organizationservice.domain.entity.Organization;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrganizationDataMapper extends BaseMapper<Organization, OrganizationDto> {

    public Organization createOrganizationCommandToOrganization(CreateOrganizationCommand createOrganizationCommand) {
        Organization organization = new Organization();
        if (Objects.nonNull(organization)) {
            BeanUtils.copyProperties(createOrganizationCommand, organization);
        }
        return organization;
    }

    public CreateOrganizationResponse organizationToCreateOrganizationResponse(Organization organization) {
        CreateOrganizationResponse createOrganizationResponse = new CreateOrganizationResponse();
        if (Objects.nonNull(organization)) {
            BeanUtils.copyProperties(organization, createOrganizationResponse);
            if (organization.getId() != null) {
                createOrganizationResponse.setOrganizationId(organization.getId());
            }
        }

        return createOrganizationResponse;
    }

    @Override
    public Organization convertToEntity(OrganizationDto dto, Object... args) {
        return null;
    }

    @Override
    public OrganizationDto convertToDto(Organization entity, Object... args) {
        OrganizationDto dto = new OrganizationDto();
        if (entity != null) {
            dto.setOrganizationId(entity.getId());
            dto.setLogo(entity.getName());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setIsDelete(entity.getIsDelete());
        }
        return dto;
    }
}
