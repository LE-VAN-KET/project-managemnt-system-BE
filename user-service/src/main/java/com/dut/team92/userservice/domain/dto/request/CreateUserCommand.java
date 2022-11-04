package com.dut.team92.userservice.domain.dto.request;

import com.dut.team92.userservice.util.validator.PasswordMatches;
import com.dut.team92.userservice.util.validator.ValidPassword;
import com.dut.team92.userservice.util.validator.ValidUsername;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class CreateUserCommand {
    @NotNull
    @NotEmpty
    @ValidUsername
    private String username;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;

    @NotNull
    @NotEmpty
    private String confirmationPassword;

    private String firstName;

    private String lastName;

    @NotNull
    @Email
    private String mailNotification;
    private UUID organizationId;
}