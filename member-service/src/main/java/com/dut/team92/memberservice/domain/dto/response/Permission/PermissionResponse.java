package com.dut.team92.memberservice.domain.dto.response.Permission;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PermissionResponse {
    private UUID Id;

    private UUID functionId;
    private boolean enable;
    private UUID screenId;
    private String functionName;

    private boolean isRequired;

    private boolean isHidden;

    private UUID dependOnFunction;
    private String code;

    public PermissionResponse(UUID id, UUID functionId, boolean enable, UUID screenId, String functionName, boolean isRequired, boolean isHidden, UUID dependOnFunction, String code) {
        Id = id;
        this.functionId = functionId;
        this.enable = enable;
        this.screenId = screenId;
        this.functionName = functionName;
        this.isRequired = isRequired;
        this.isHidden = isHidden;
        this.dependOnFunction = dependOnFunction;
        this.code = code;
    }
}
