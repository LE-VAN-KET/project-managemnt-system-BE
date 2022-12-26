package com.dut.team92.memberservice.services;


import com.dut.team92.memberservice.domain.dto.request.CheckPermissionModel;
import com.dut.team92.memberservice.domain.dto.request.permission.AddUpdateRoleRequest;
import com.dut.team92.memberservice.domain.dto.request.permission.RolesPermissionDto;
import com.dut.team92.memberservice.domain.dto.response.Permission.GroupsOfScreenResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse;
import com.dut.team92.memberservice.domain.dto.response.Response;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    /// Get list roles of system
    Response getRoles(UUID organizationId);

    Response getListPermission(Long roleId);

    /// save permission receive from client and return status of database change
    Response savePermission(long role_id,RolesPermissionDto data);
    Response addNewRoles(AddUpdateRoleRequest data);
    Response updateRoles(long roleId,AddUpdateRoleRequest data);
    Response checkPermission(CheckPermissionModel data);
}