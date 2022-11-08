package com.dut.team92.organizationservice.services.mapper;

import com.dut.team92.organizationservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.organizationservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.organizationservice.domain.entity.Organization;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrganizationDataMapper {

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
        }
        createOrganizationResponse.setOrganizationId(organization.getId());
        return createOrganizationResponse;
    }
}
