package com.dut.team92.memberservice.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@NotNull
@Getter
@Setter
public class CreateMemberDto {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @Email
    private String email;
    private String firstName;
    private String lastName;

    private String displayName;
    private UUID organizationId;

    public CreateMemberDto(String username, String password, String email, String firstName, String lastName, String displayName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
    }
}
