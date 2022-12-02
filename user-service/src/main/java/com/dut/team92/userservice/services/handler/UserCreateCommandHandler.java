package com.dut.team92.userservice.services.handler;

import com.dut.team92.common.exception.CommonAuthException;
import com.dut.team92.common.exception.CommonNotFoundException;
import com.dut.team92.userservice.domain.dto.event.UserCreatedEvent;
import com.dut.team92.userservice.domain.dto.request.CreateUserAdminOrganizationCommand;
import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.userservice.domain.dto.response.CreateUserAdminOrganizationResponse;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.domain.entity.UserInformation;
import com.dut.team92.userservice.exception.BadRequestExceptionOrganization;
import com.dut.team92.userservice.exception.EmailAlreadyExistsException;
import com.dut.team92.userservice.exception.SaveUserFailedException;
import com.dut.team92.userservice.exception.UsernameAlreadyExistsException;
import com.dut.team92.userservice.services.UserInformationService;
import com.dut.team92.userservice.services.UserService;
import com.dut.team92.userservice.services.mapper.UserDataMapper;
import com.dut.team92.userservice.services.mapper.UserInformationDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Slf4j
public class UserCreateCommandHandler {
    private UserDataMapper userDataMapper;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UserInformationDataMapper userInformationDataMapper;
    private UserInformationService userInformationService;

    @Autowired
    public UserCreateCommandHandler(UserDataMapper userDataMapper, UserService userService,
                                    PasswordEncoder passwordEncoder, UserInformationDataMapper userInformationDataMapper,
                                    UserInformationService userInformationService) {
        this.userDataMapper = userDataMapper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userInformationDataMapper = userInformationDataMapper;
        this.userInformationService = userInformationService;
    }

    @Transactional(rollbackFor = { SaveUserFailedException.class, Exception.class })
    public UserCreatedEvent createUser(CreateUserCommand createUserCommand) {
        User user = userDataMapper.createUserCommandToUser(createUserCommand);
        UserInformation userInformation = userInformationDataMapper
                .createUserCommandToUserInformation(createUserCommand);
        return saveUser(user, userInformation);
    }

    private UserCreatedEvent saveUser(User user, UserInformation userInformation) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserCreatedEvent userCreatedEvent = validateUser(user);

        User savedUser = userService.create(user);
        if (savedUser == null) {
            throw new SaveUserFailedException();
        }

        userInformation.setUser(savedUser);
        userInformationService.create(userInformation);

        userCreatedEvent.getUser().setId(savedUser.getId());
        return userCreatedEvent;
    }

    @Transactional(rollbackFor = { SaveUserFailedException.class, Exception.class })
    public UserCreatedEvent createAdminForOrganization(CreateUserAdminOrganizationCommand createUserCommand) {
        User user = userDataMapper.createAdminOrganizationCommandToUser(createUserCommand);
        user.setIsOrganizerAdmin(true);
        UserInformation userInformation = userInformationDataMapper
                .createUserCommandToUserInformation(createUserCommand);
        return saveUser(user, userInformation);
    }

    private UserCreatedEvent validateUser(User user) {
        if (userService.isUsernameExist(user.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        if (userService.isMailNotification(user.getMailNotification())) {
            throw new EmailAlreadyExistsException();
        }

        return new UserCreatedEvent(user, ZonedDateTime.now(ZoneId.of("GMT+7")));
    }

    public CreateUserAdminOrganizationResponse throwExceptionOrganizationService(CreateOrganizationResponse response) {
        String errorMessage = response.getMessage();
        switch (response.getCode()) {
            case 400:
                log.error("Error in request went through feign client {} ", errorMessage);
                throw new BadRequestExceptionOrganization( 400,errorMessage);
            case 401:
                log.error("Unauthorized Request Through Feign {} ", errorMessage);
                throw  new CommonAuthException(401, errorMessage);
            case 404:
                log.error("Unidentified Request Through Feign {} ", errorMessage);
                throw  new CommonNotFoundException(404, errorMessage);
            default:
                log.error("Error in request went through feign client {} ", errorMessage);
                throw  new RuntimeException(errorMessage);
        }
    }

}
