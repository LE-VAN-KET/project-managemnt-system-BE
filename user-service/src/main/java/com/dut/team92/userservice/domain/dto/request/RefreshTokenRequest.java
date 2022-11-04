package com.dut.team92.userservice.domain.dto.request;

import com.dut.team92.common.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RefreshTokenRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private String token;
    private String tokenType;
}
