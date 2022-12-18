package com.dut.team92.memberservice.controller;

import com.dut.team92.memberservice.domain.dto.MemberDto;
import com.dut.team92.memberservice.domain.dto.request.AddMemberToProjectRequest;
import com.dut.team92.memberservice.domain.dto.request.CheckPermissionModel;
import com.dut.team92.memberservice.domain.dto.request.permission.AddUpdateRoleRequest;
import com.dut.team92.memberservice.domain.dto.request.permission.RolesPermissionDto;
import com.dut.team92.memberservice.domain.dto.response.CheckExistMemberResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.GroupsOfScreenResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse;
import com.dut.team92.memberservice.domain.dto.response.Response;
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

    @GetMapping("{role_id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getListPermission(@PathVariable("role_id") Long roleId) {
        return permissionService.getListPermission(roleId);
    }

    @PostMapping("{role_id}")
    @ResponseStatus(HttpStatus.OK)
    public Response changePermission(@PathVariable long role_id ,@RequestBody RolesPermissionDto data) {
        return permissionService.savePermission(role_id, data);
    }

    @PostMapping("check")
    @ResponseStatus(HttpStatus.OK)
    public Response checkPermission(@RequestBody CheckPermissionModel data) {
        return permissionService.checkPermission(data);
    }
//
//    @PostMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public void addMemberToProject(@Valid @RequestBody AddMemberToProjectRequest command) {
//        memberService.addMemberToProject(command);
//    }

}
