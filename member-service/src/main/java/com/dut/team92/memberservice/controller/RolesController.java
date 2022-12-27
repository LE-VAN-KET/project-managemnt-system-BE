package com.dut.team92.memberservice.controller;

import com.dut.team92.memberservice.domain.dto.request.CheckPermissionModel;
import com.dut.team92.memberservice.domain.dto.request.permission.AddUpdateRoleRequest;
import com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse;
import com.dut.team92.memberservice.domain.dto.response.Response;
import com.dut.team92.memberservice.services.PermissionService;
import com.dut.team92.memberservice.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolesController {
    private final RoleService roleService;

    @Autowired
    private final PermissionService permissionService;

    @GetMapping
    public Object getRolesByUserId(@RequestParam("user_id") String userId) {
//        Response res = permissionService.hassAccess("GET_USER_ROLES");
//        if(!res.getRspCode().equals("200"))
//        {
//            return res;
//        }
        return roleService.getAllRolesByUserId(UUID.fromString(userId));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Response getListRoles(@RequestParam(required=false) UUID organizationId) {
        Response res = permissionService.hassAccess("GET_LIST_ROLES_ORGANIZER");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return permissionService.getRoles(organizationId);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Response addRoles(@RequestBody AddUpdateRoleRequest data) {
        Response res = permissionService.hassAccess("ADD_ROLES");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return permissionService.addNewRoles(data);
    }

    @PostMapping("{role_id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateRoles(@PathVariable long role_id ,@RequestBody AddUpdateRoleRequest data) {
        Response res = permissionService.hassAccess("UPDATE_ROLES");
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return permissionService.updateRoles( role_id, data);
    }
}
