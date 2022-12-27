package com.dut.team92.issuesservice.controller;

import com.dut.team92.issuesservice.domain.dto.IssuesTypeDto;
import com.dut.team92.issuesservice.domain.dto.response.Response;
import com.dut.team92.issuesservice.proxy.MemberServiceProxy;
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

    @Autowired
    private MemberServiceProxy memberServiceProxy;

    @GetMapping("/{issues_type_name}")
    public Object getOneIssuesTypeByOrganizationIdAndName(@PathVariable("issues_type_name") String name,
                                                                 @RequestParam(required = false) String organizationId,
                                                                 @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                                                 @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_ISSUES_TYPE" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        UUID organId = organizationId != null ? UUID.fromString(organizationId): null;
        return issuesTypeService.getOneIssuesTypeByNameAndOrganizationId(name, organId);
    }

    @GetMapping()
    public Object getAllIssuesTypeByOrganizationIdAndName(@RequestParam(required = false)
                                                                           String organizationId,
                                                          @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                                          @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_LIST_ISSUES_TYPE" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        UUID organId = organizationId != null ? UUID.fromString(organizationId): null;
        return issuesTypeService.getAllIssuesTypeByOrganizationId(organId);
    }
}
