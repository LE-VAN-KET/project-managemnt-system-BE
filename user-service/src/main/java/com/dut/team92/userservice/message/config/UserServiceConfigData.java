package com.dut.team92.userservice.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "user-service")
public class UserServiceConfigData {
    private String organizationRequestTopicName;
    private String organizationResponseTopicName;
    private String memberRequestTopicName;
    private String memberResponseTopicName;
    private String removeUserRequestTopicName;
    private String updateUserRequestTopicName;
}
