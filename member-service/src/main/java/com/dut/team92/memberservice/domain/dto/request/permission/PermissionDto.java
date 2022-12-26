package com.dut.team92.memberservice.domain.dto.request.permission;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PermissionDto {
    private UUID id;
    private boolean enable;
}
