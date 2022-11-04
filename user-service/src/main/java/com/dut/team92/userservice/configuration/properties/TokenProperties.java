package com.dut.team92.userservice.configuration.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.security.authentication")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenProperties {
    private Long accessTokenValidityInSeconds;
    private Long refreshTokenValidityInSeconds;
}
