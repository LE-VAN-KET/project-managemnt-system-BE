package com.dut.team92.memberservice.controller;

import com.dut.team92.memberservice.domain.dto.MemberDto;
import com.dut.team92.memberservice.domain.dto.request.AddMemberToProjectRequest;
import com.dut.team92.memberservice.domain.dto.response.CheckExistMemberResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.GroupsOfScreenResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse;
import com.dut.team92.memberservice.services.MemberService;
import com.dut.team92.memberservice.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/permissions")
public class PermissionsController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("permissions")
    @ResponseStatus(HttpStatus.OK)
    public List<GroupsOfScreenResponse> getListPermission(@RequestParam("role_id") Long roleId) {
        List<GroupsOfScreenResponse> permissions = permissionService.getListPermission(roleId);
        return permissions;
    }

    @GetMapping("roles")
    @ResponseStatus(HttpStatus.OK)
    public List<RolesResponse> getListRoles(@RequestParam UUID organizationId) {
        List<RolesResponse> roles = permissionService.getRoles(organizationId);
        return roles;
    }
//
//    @PostMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public void addMemberToProject(@Valid @RequestBody AddMemberToProjectRequest command) {
//        memberService.addMemberToProject(command);
//    }

}
