package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.OrganizationDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.organizationservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.organizationservice.domain.entity.Organization;
import com.dut.team92.organizationservice.exception.OrganizationNotFound;
import com.dut.team92.organizationservice.repository.OrganizationRepository;
import com.dut.team92.organizationservice.services.handler.CreateOrganizationCommandHandler;
import com.dut.team92.organizationservice.services.mapper.OrganizationDataMapper;
import com.dut.team92.organizationservice.utils.PropertyPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrganizationServiceImpl implements OrganizationService{
    private OrganizationRepository organizationRepository;
    private OrganizationDataMapper organizationDataMapper;
    private CreateOrganizationCommandHandler createOrganizationCommandHandler;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   OrganizationDataMapper organizationDataMapper,
                                   CreateOrganizationCommandHandler createOrganizationCommandHandler) {
        this.organizationRepository = organizationRepository;
        this.organizationDataMapper = organizationDataMapper;
        this.createOrganizationCommandHandler = createOrganizationCommandHandler;
    }

    @Override
    public List<Organization> getAll() {
        return null;
    }

    @Override
    public Organization get(Long id) {
        return null;
    }

    @Override
    public Organization create(Organization entity) {
        return organizationRepository.save(entity);
    }

    @Override
    public Organization update(Organization entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public CreateOrganizationResponse createOrganization(CreateOrganizationCommand createOrganizationCommand) {
        return organizationDataMapper.organizationToCreateOrganizationResponse(
                createOrganizationCommandHandler.createOrganization(createOrganizationCommand));
    }

    @Override
    public boolean isExistOrganizationById(UUID organizationId) {
        return organizationRepository.existsById(organizationId);
    }

    @Override
    @Transactional
    public OrganizationDto update(OrganizationDto organizationDto, UUID organizationId) {
        Organization existOrganization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFound(404, "Organization not found!"));
        BeanUtils.copyProperties(organizationDto, existOrganization, PropertyPojo.getNullPropertyNames(organizationDto));
        return organizationDataMapper.convertToDto(
                organizationRepository.save(existOrganization)
        );
    }

    @Override
    public OrganizationDto getOne(UUID organizationId) {
        Organization existOrganization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFound(404, "Organization not found!"));
        return organizationDataMapper.convertToDto(existOrganization);
    }
}
