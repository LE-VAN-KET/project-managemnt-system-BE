package com.dut.team92.memberservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class UserIdWithEmail {
    private UUID id;

    @Email
    private String mailNotification;
}
