package com.dut.team92.userservice.services.mapper;

import com.dut.team92.common.enums.UserStatus;
import com.dut.team92.userservice.domain.dto.UserDto;
import com.dut.team92.userservice.domain.dto.request.CreateMemberDto;
import com.dut.team92.userservice.domain.dto.request.CreateUserAdminOrganizationCommand;
import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.userservice.domain.dto.response.CreateUserAdminOrganizationResponse;
import com.dut.team92.userservice.domain.dto.response.CreateUserResponse;
import com.dut.team92.userservice.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDataMapper {
    private final PasswordEncoder passwordEncoder;

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

    public List<User> createMemberDtoToUser(List<CreateMemberDto> createMemberDtoList) {
        return createMemberDtoList.stream().filter(Objects::nonNull).map(m -> {
            User user = new User();
            BeanUtils.copyProperties(m, user);
            user.setPassword(passwordEncoder.encode(m.getPassword()));
            user.setMailNotification(m.getEmail());
            user.setStatus(UserStatus.ACTIVE);
            user.setIsDelete(false);
            return user;
        }).collect(Collectors.toList());
    }

    public List<UserDto> userListToUserDtoList(List<User> users) {
        return users.stream().map(u -> UserDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .mailNotification(u.getMailNotification())
                .status(u.getStatus())
                .isDelete(u.getIsDelete())
                .organizationId(u.getOrganizationId())
                .build()).collect(Collectors.toList());
    }
}
