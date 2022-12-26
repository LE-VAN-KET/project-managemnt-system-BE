package com.dut.team92.memberservice.domain.dto.response.Permission;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ScreenResponse {
    private UUID Id;

    private int groupId;

    private String screenName;

    private String code;

    private String description;

    /// List of permission(function) can be assign on this screen
    private List<PermissionResponse> Permissions;

    public ScreenResponse(UUID id, int groupId, String screenName, String code, String description) {
        Id = id;
        this.groupId = groupId;
        this.screenName = screenName;
        this.code = code;
        this.description = description;
    }
}
