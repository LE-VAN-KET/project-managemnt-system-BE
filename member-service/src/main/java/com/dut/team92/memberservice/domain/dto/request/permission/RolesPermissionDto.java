package com.dut.team92.memberservice.domain.dto.request.permission;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RolesPermissionDto {
    private UUID rolesId;
    private List<PermissionDto> permissions;
}
