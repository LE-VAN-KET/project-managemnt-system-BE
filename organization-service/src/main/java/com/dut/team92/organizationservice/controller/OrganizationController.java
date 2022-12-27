package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.domain.dto.OrganizationDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.organizationservice.domain.dto.response.CheckOrganizationExistResponse;
import com.dut.team92.organizationservice.domain.dto.response.Response;
import com.dut.team92.organizationservice.proxy.MemberServiceProxy;
import com.dut.team92.organizationservice.services.BoardService;
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

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberServiceProxy memberServiceProxy;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object createOrganization(@Valid @RequestBody CreateOrganizationCommand createOrganizationCommand,
                                     @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                     @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("ADD_ORGANIZATION" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return organizationService.createOrganization(createOrganizationCommand);
    }

    @GetMapping("/check/{organization_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object checkOrganizationExist(@PathVariable("organization_id") String organizationId,
                                         @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                         @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("CHECK_EXIST_ORGANIZATION" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        boolean existOrganization = organizationService.isExistOrganizationById(UUID.fromString(organizationId));
        return CheckOrganizationExistResponse.builder().isExistOrganization(existOrganization).build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object updateOrganization(@PathVariable("id") String organizationId,
                                              @RequestBody OrganizationDto organizationDto,
                                     @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                     @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("UPDATE_ORGANIZATION" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return organizationService.update(organizationDto, UUID.fromString(organizationId));
    }

    @GetMapping("/{id}")
    public Object getOneOrganization(@PathVariable("id") String organizationId,
                                     @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                     @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_DETAIL_ORGANIZATION" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return organizationService.getOne(UUID.fromString(organizationId));
    }
}
