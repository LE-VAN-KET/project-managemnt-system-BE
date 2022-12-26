package com.dut.team92.memberservice.controller;

import com.dut.team92.memberservice.domain.dto.request.permission.AddUpdateRoleRequest;
import com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse;
import com.dut.team92.memberservice.domain.dto.response.Response;
import com.dut.team92.memberservice.services.PermissionService;
import com.dut.team92.memberservice.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolesController {
    private final RoleService roleService;

    private final PermissionService permissionService;

    @GetMapping
    public List<String> getRolesByUserId(@RequestParam("user_id") String userId) {
        return roleService.getAllRolesByUserId(UUID.fromString(userId));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Response getListRoles(@RequestParam(required=false) UUID organizationId) {
        return permissionService.getRoles(organizationId);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Response addRoles(@RequestBody AddUpdateRoleRequest data) {
        return permissionService.addNewRoles(data);
    }

    @PostMapping("{role_id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateRoles(@PathVariable long role_id ,@RequestBody AddUpdateRoleRequest data) {
        return permissionService.updateRoles( role_id, data);
    }
}
