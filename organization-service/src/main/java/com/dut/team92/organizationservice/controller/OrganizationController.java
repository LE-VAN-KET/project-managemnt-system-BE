package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.organizationservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.organizationservice.services.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public boolean checkOrganizationExist(@PathVariable("organization_id") String organizationId) {
        return organizationService.isExistOrganizationById(UUID.fromString(organizationId));
    }
}
