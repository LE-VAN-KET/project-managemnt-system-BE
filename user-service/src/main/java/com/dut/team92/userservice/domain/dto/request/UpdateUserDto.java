package com.dut.team92.userservice.domain.dto.request;

import com.dut.team92.common.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Getter
public class UpdateUserDto implements Serializable {
    private String username;
    private String mailNotification;
    private String firstName;
    private String lastName;
    private String displayName;
    private UserStatus status;
}
