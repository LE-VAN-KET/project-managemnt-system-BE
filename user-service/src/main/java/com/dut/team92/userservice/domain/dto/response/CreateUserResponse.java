package com.dut.team92.userservice.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Getter
public class CreateUserResponse {
    @NotNull
    private UUID userId;

    @NotNull
    private String message;
}
