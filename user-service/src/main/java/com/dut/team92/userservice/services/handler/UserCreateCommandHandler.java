package com.dut.team92.userservice.services.handler;

import com.dut.team92.userservice.domain.dto.event.UserCreatedEvent;
import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.domain.entity.UserInformation;
import com.dut.team92.userservice.exception.EmailAlreadyExistsException;
import com.dut.team92.userservice.exception.SaveUserFailedException;
import com.dut.team92.userservice.exception.UsernameAlreadyExistsException;
import com.dut.team92.userservice.repository.UserRepository;
import com.dut.team92.userservice.services.UserInformationService;
import com.dut.team92.userservice.services.UserService;
import com.dut.team92.userservice.services.mapper.UserDataMapper;
import com.dut.team92.userservice.services.mapper.UserInformationDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserCreatedEvent userCreatedEvent = validateUser(user);

        User savedUser = userService.create(user);
        if (savedUser == null) {
            throw new SaveUserFailedException();
        }
        UserInformation userInformation = userInformationDataMapper
                .createUserCommandToUserInformation(createUserCommand);
        userInformation.setUser(savedUser);
        userInformationService.create(userInformation);

        userCreatedEvent.getUser().setId(savedUser.getId());
        return userCreatedEvent;
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

}
