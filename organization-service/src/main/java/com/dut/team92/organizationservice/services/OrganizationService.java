package com.dut.team92.organizationservice.services;

import com.dut.team92.common.service.RepositoryService;
import com.dut.team92.organizationservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.organizationservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.organizationservice.domain.entity.Organization;

public interface OrganizationService extends RepositoryService<Organization> {
    CreateOrganizationResponse createOrganization(CreateOrganizationCommand createOrganizationCommand);
}
