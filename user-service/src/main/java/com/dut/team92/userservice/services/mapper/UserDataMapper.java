package com.dut.team92.userservice.services.mapper;

import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.dto.response.CreateUserResponse;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.domain.entity.UserInformation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDataMapper {

    public User createUserCommandToUser(CreateUserCommand createUserCommand) {
        User user = new User();
        if (createUserCommand != null) {
            user.setId(UUID.randomUUID());
            user.setUsername(createUserCommand.getUsername());
            user.setPassword(createUserCommand.getPassword());
            user.setMailNotification(createUserCommand.getMailNotification());
            user.setOrganizationId(createUserCommand.getOrganizationId());
        }

        return user;
    }

    public CreateUserResponse userToCreateUserResponse(User user, String message) {
        return CreateUserResponse.builder()
                .userId(user.getId())
                .message(message)
                .build();
    }
}
