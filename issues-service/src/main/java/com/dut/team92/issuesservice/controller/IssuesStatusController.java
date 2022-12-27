package com.dut.team92.issuesservice.controller;

import com.dut.team92.issuesservice.domain.dto.IssuesStatusDto;
import com.dut.team92.issuesservice.domain.dto.response.Response;
import com.dut.team92.issuesservice.proxy.MemberServiceProxy;
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

    @Autowired
    private MemberServiceProxy memberServiceProxy;

    @GetMapping("/{issues_status_name}")
    public Object getOneIssuesStatus(@PathVariable("issues_status_name") String name,
                                              @RequestParam(required = false) String organizationId,
                                              @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                              @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_ISSUES_STATUS" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        UUID organId = organizationId != null ? UUID.fromString(organizationId): null;
        return issuesStatusService.getOneIssuesStatusByNameAndOrganizationId(name, organId);
    }

    @GetMapping
    public Object getAllIssuesStatusByOrganizationId(@RequestParam(required = false)
                                                                    String organizationId,
                                                     @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                                     @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_LIST_ISSUES_STATUS" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        UUID organId = organizationId != null ? UUID.fromString(organizationId): null;
        return issuesStatusService.getAllIssuesStatusByOrganizationId(organId);
    }
}
