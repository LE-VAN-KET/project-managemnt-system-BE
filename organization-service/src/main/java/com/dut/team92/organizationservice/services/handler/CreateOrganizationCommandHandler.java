package com.dut.team92.organizationservice.services.handler;

import com.dut.team92.organizationservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.organizationservice.domain.entity.Organization;
import com.dut.team92.organizationservice.exception.OrganizationNameExisted;
import com.dut.team92.organizationservice.repository.OrganizationRepository;
import com.dut.team92.organizationservice.services.mapper.OrganizationDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateOrganizationCommandHandler {
    private OrganizationDataMapper organizationDataMapper;
    private OrganizationRepository organizationRepository;

    @Autowired
    public CreateOrganizationCommandHandler(OrganizationDataMapper organizationDataMapper, OrganizationRepository organizationRepository) {
        this.organizationDataMapper = organizationDataMapper;
        this.organizationRepository = organizationRepository;
    }

    public Organization createOrganization(CreateOrganizationCommand createOrganizationCommand) {
        Organization organization = organizationDataMapper
                .createOrganizationCommandToOrganization(createOrganizationCommand);
        validateOrganization(organization);
        return organizationRepository.save(organization);
    }

    private void validateOrganization(Organization organization) {
        if (organizationRepository.existsByName(organization.getName())) {
            throw new OrganizationNameExisted(400, "Organization Name is exist with name "
                    + organization.getName());
        }
    }
}
