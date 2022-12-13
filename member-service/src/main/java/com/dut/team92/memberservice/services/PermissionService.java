package com.dut.team92.memberservice.services;


import com.dut.team92.memberservice.domain.dto.request.permission.AddUpdateRoleRequest;
import com.dut.team92.memberservice.domain.dto.request.permission.RolesPermissionDto;
import com.dut.team92.memberservice.domain.dto.response.Permission.GroupsOfScreenResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    /// Get list roles of system
    List<RolesResponse> getRoles(UUID organizationId);

    List<GroupsOfScreenResponse> getListPermission(Long roleId);

    /// save permission receive from client and return status of database change
    boolean savePermission(long role_id,RolesPermissionDto data);
    boolean addNewRoles(AddUpdateRoleRequest data);
    boolean updateRoles(long roleId,AddUpdateRoleRequest data);
}