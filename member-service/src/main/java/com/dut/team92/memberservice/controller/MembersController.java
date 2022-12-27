package com.dut.team92.memberservice.controller;

import com.dut.team92.memberservice.domain.dto.MemberDto;
import com.dut.team92.memberservice.domain.dto.UserDto;
import com.dut.team92.memberservice.domain.dto.request.AddMemberToProjectRequest;
import com.dut.team92.memberservice.domain.dto.request.CheckPermissionModel;
import com.dut.team92.memberservice.domain.dto.response.CheckExistMemberResponse;
import com.dut.team92.memberservice.domain.dto.response.ProjectResponse;
import com.dut.team92.memberservice.domain.dto.response.Response;
import com.dut.team92.memberservice.services.MemberService;
import com.dut.team92.memberservice.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/members")
public class MembersController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("check/{member_id}/projects/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object checkExistMemberInProject(@PathVariable("member_id") String memberId,
                                                              @PathVariable("project_id") String projectId) {
        Response res = permissionService.hassAccess("PROJECT_CHECK_EXIST_MEMBER");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        boolean existMember = memberService.existMemberInProject(UUID.fromString(memberId),
                UUID.fromString(projectId));
        return CheckExistMemberResponse.builder().isExistMember(existMember).build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Object addMemberToProject(@Valid @RequestBody AddMemberToProjectRequest command) {
        Response res = permissionService.hassAccess("PROJECT_ADD_MEMBER");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return memberService.addMemberToProject(command);
    }

    @GetMapping("/organization/{organization_id}/search")
    public Object searchMemberInOrganization(@PathVariable("organization_id") String organizationId,
                                                       @RequestParam(value = "keyword", required = false)
                                                       String keyword) {
        Response res = permissionService.hassAccess("ORGANIZATION_SEARCH_MEMBER");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return memberService.searchMemberInOrganization(UUID.fromString(organizationId), keyword);
    }

    @GetMapping("/projects/{project_id}/search")
    public Object searchMemberInProject(@PathVariable("project_id") String projectId,
                                                       @RequestParam(value = "keyword", required = false)
                                                       String keyword) {
        Response res = permissionService.hassAccess("PROJECT_SEARCH_MEMBER");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return memberService.searchMemberInProject(UUID.fromString(projectId), keyword);
    }

    @GetMapping("/project-ids")
    public Object getListProjectIdMemberAttending(@RequestParam("organization_id") String organizationId,
                                                                 @RequestHeader("Authorization") String authorization) {
        Response res = permissionService.hassAccess("GET_LIST_PROJECT_MEMBER_ATTENDING");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return memberService.getAllProjectIdByUserIdAndOrganizationId(UUID.fromString(organizationId));
    }

    @GetMapping("/{id}")
    public Object getMemberByMemberId(@PathVariable("id") String memberId) {
        Response res = permissionService.hassAccess("GET_MEMBER_BY_ID");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return memberService.getMemberById(UUID.fromString(memberId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object removeMemberInTheProject(@PathVariable("id") String memberId) {
        Response res = permissionService.hassAccess("PROJECT_REMOVE_MEMBER");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        memberService.removeMemberInTheProject(UUID.fromString(memberId));
        return null;
    }

}
