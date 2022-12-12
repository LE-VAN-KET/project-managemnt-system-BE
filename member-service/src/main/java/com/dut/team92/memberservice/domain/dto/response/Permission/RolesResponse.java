package com.dut.team92.memberservice.domain.dto.response.Permission;

import java.util.UUID;

public class RolesResponse {
    private Long id;
    private String name;
    private int type;

    public RolesResponse(Long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
