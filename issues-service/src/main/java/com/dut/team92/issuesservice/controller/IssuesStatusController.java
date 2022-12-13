package com.dut.team92.issuesservice.controller;

import com.dut.team92.issuesservice.domain.dto.IssuesStatusDto;
import com.dut.team92.issuesservice.services.IssuesStatusService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/issues-status")
@SecurityRequirement(name = "BearerAuth")
public class IssuesStatusController {

    @Autowired
    private IssuesStatusService issuesStatusService;

    @GetMapping("/{issues_status_name}")
    public IssuesStatusDto getOneIssuesStatus(@PathVariable("issues_status_name") String name,
                                              @RequestParam(required = false) String organizationId) {
        UUID organId = organizationId != null ? UUID.fromString(organizationId): null;
        return issuesStatusService.getOneIssuesStatusByNameAndOrganizationId(name, organId);
    }

    @GetMapping
    public List<IssuesStatusDto> getAllIssuesStatusByOrganizationId(@RequestParam(required = false)
                                                                    String organizationId) {
        UUID organId = organizationId != null ? UUID.fromString(organizationId): null;
        return issuesStatusService.getAllIssuesStatusByOrganizationId(organId);
    }
}
