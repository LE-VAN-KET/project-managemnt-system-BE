package com.dut.team92.memberservice.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CheckPermissionModel {
    private UUID projectId;
    private UUID functionId;
}
