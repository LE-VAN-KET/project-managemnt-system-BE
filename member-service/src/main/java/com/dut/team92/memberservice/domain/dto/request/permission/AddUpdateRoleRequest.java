package com.dut.team92.memberservice.domain.dto.request.permission;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddUpdateRoleRequest {
    private String name;
    private String code;
    private String description;
    private UUID organizationId;
    public AddUpdateRoleRequest()
    {
        name = "";
        code = "";
        description = "";
    }
}
