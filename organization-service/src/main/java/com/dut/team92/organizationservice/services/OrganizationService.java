package com.dut.team92.organizationservice.services;

import com.dut.team92.common.service.RepositoryService;
import com.dut.team92.organizationservice.domain.dto.OrganizationDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.organizationservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.organizationservice.domain.entity.Organization;

import java.util.UUID;

public interface OrganizationService extends RepositoryService<Organization> {
    CreateOrganizationResponse createOrganization(CreateOrganizationCommand createOrganizationCommand);
    boolean isExistOrganizationById(UUID organizationId);
    OrganizationDto update(OrganizationDto organizationDto, UUID organizationId);
    OrganizationDto getOne(UUID organizationId);

}
