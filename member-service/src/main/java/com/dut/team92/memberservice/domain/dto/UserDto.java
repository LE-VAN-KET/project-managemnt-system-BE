package com.dut.team92.memberservice.domain.dto;

import com.dut.team92.common.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private UUID id;
    private String username;
    private Boolean isOrganizerAdmin;
    private Boolean isSystemAdmin;
    private UserStatus status;
    private Boolean isDelete;
    @Email
    private String mailNotification;
    private UUID organizationId;

    private String firstName;
    private String lastName;
    private String displayName;
}
