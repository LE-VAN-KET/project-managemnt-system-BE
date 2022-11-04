package com.dut.team92.userservice.domain.dto.request;

import com.dut.team92.userservice.util.validator.ValidPassword;
import com.dut.team92.userservice.util.validator.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginUserRequest {
    @NotNull
    @NotEmpty(message = "Username is required")
    @ValidUsername
    private String username;

    @NotNull
    @NotEmpty(message = "Password is required")
    @ValidPassword
    private String password;
}
