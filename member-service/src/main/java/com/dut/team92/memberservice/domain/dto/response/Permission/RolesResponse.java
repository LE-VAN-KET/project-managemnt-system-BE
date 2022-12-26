package com.dut.team92.memberservice.domain.dto.response.Permission;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RolesResponse {
    private long id;
    private String name;
    private int type;

    public RolesResponse(long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
