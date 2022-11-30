package com.dut.team92.issuesservice.controller;

import com.dut.team92.issuesservice.domain.dto.IssuesTypeDto;
import com.dut.team92.issuesservice.services.IssuesTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/issues-types")
@SecurityRequirement(name = "BearerAuth")
public class IssuesTypeController {

    @Autowired
    private IssuesTypeService issuesTypeService;

    @GetMapping("/{issues_type_name}")
    public IssuesTypeDto getOneIssuesTypeByOrganizationIdAndName(@PathVariable("issues_type_name") String name,
                                                                 @RequestParam(required = false) String organizationId) {
        UUID organId = organizationId != null ? UUID.fromString(organizationId): null;
        return issuesTypeService.getOneIssuesTypeByNameAndOrganizationId(name, organId);
    }

    @GetMapping()
    public List<IssuesTypeDto> getOneIssuesTypeByOrganizationIdAndName(@RequestParam(required = false)
                                                                           String organizationId) {
        UUID organId = organizationId != null ? UUID.fromString(organizationId): null;
        return issuesTypeService.getAllIssuesTypeByOrganizationId(organId);
    }
}
