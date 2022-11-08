package com.dut.team92.userservice.services.mapper;

import com.dut.team92.userservice.domain.dto.request.CreateUserAdminOrganizationCommand;
import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.userservice.domain.dto.response.CreateUserAdminOrganizationResponse;
import com.dut.team92.userservice.domain.dto.response.CreateUserResponse;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.domain.entity.UserInformation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDataMapper {

    public User createUserCommandToUser(CreateUserCommand createUserCommand) {
        User user = new User();
        if (createUserCommand != null) {
            BeanUtils.copyProperties(createUserCommand, user);
        }

        return user;
    }
    public User createAdminOrganizationCommandToUser(CreateUserAdminOrganizationCommand createUserCommand) {
        User user = new User();
        if (createUserCommand != null) {
            BeanUtils.copyProperties(createUserCommand, user);
        }

        return user;
    }

    public CreateUserResponse userToCreateUserResponse(User user, String message) {
        return CreateUserResponse.builder()
                .userId(user.getId())
                .message(message)
                .build();
    }

    public CreateUserAdminOrganizationResponse userToCreateUserAdminOrganizationResponse(User user,
                                                                                         String message,
                                                                                         CreateOrganizationResponse
                                                                                         createOrganizationResponse) {
        CreateUserAdminOrganizationResponse response = new CreateUserAdminOrganizationResponse(user.getId(), message);
        if (createOrganizationResponse != null) {
            BeanUtils.copyProperties(createOrganizationResponse, response);
        }
        return response;
    }
}
