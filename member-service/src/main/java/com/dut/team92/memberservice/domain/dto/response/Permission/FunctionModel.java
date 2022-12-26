package com.dut.team92.memberservice.domain.dto.response.Permission;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FunctionModel {
    private UUID functionId;
    private boolean isRequired;
    private UUID screenId;
    public FunctionModel(UUID functionId, boolean isRequired, UUID screenId)
    {
        this.functionId = functionId;
        this.isRequired = isRequired;
        this.screenId = screenId;
    }
}
