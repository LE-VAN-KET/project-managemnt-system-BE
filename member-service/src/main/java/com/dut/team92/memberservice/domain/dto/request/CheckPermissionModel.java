package com.dut.team92.memberservice.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CheckPermissionModel {
    private String projectId;
    private String functionCode;
    public CheckPermissionModel(String projectId, String functionCode)
    {
        this.projectId = projectId;
        this.functionCode = functionCode;
    }
}
