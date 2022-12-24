package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.domain.dto.OrganizationDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.organizationservice.domain.dto.response.CheckOrganizationExistResponse;
import com.dut.team92.organizationservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.organizationservice.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrganizationResponse createOrganization(@Valid  @RequestBody CreateOrganizationCommand
                                                                 createOrganizationCommand) {
        return organizationService.createOrganization(createOrganizationCommand);
    }

    @GetMapping("/check/{organization_id}")
    @ResponseStatus(HttpStatus.OK)
    public CheckOrganizationExistResponse checkOrganizationExist(@PathVariable("organization_id") String organizationId) {
        boolean existOrganization = organizationService.isExistOrganizationById(UUID.fromString(organizationId));
        return CheckOrganizationExistResponse.builder().isExistOrganization(existOrganization).build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationDto updateOrganization(@PathVariable("id") String organizationId,
                                              @RequestBody OrganizationDto organizationDto) {
        return organizationService.update(organizationDto, UUID.fromString(organizationId));
    }

    @GetMapping("/{id}")
    public OrganizationDto getOneOrganization(@PathVariable("id") String organizationId) {
        return organizationService.getOne(UUID.fromString(organizationId));
    }
}
