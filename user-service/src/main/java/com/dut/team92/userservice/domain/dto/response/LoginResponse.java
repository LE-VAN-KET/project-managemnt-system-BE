package com.dut.team92.userservice.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class LoginResponse implements Serializable {
    private UUID userId;
    private String organizationId;
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String refreshToken;
    private Long refreshExpiresIn;
    private List<String> roles;
}
