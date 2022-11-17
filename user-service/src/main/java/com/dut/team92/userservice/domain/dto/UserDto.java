package com.dut.team92.userservice.domain.dto;

import com.dut.team92.common.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class UserDto {
    private UUID id;
    private String username;

    private UserStatus status;

    private Boolean isDelete;
    private String mailNotification;
    private UUID organizationId;

    private String firstName;
    private String lastname;
    private String displayName;
}
