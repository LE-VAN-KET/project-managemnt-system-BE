package com.dut.team92.userservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CreateUserResponse {
    @NotNull
    private UUID userId;

    @NotNull
    private String message;
}
