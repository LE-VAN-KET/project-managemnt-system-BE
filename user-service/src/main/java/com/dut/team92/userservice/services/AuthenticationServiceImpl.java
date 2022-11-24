package com.dut.team92.userservice.services;

import com.dut.team92.common.security.TokenProvider;
import com.dut.team92.userservice.configuration.properties.TokenProperties;
import com.dut.team92.userservice.domain.dto.event.UserCreatedEvent;
import com.dut.team92.userservice.domain.dto.request.*;
import com.dut.team92.userservice.domain.dto.response.CreateOrganizationResponse;
import com.dut.team92.userservice.domain.dto.response.CreateUserAdminOrganizationResponse;
import com.dut.team92.userservice.domain.dto.response.CreateUserResponse;
import com.dut.team92.userservice.domain.dto.response.LoginResponse;
import com.dut.team92.userservice.domain.entity.TokenPair;
import com.dut.team92.userservice.exception.InvalidAccessTokenException;
import com.dut.team92.userservice.exception.InvalidRefreshTokenException;
import com.dut.team92.userservice.exception.UserNotFoundException;
import com.dut.team92.userservice.proxy.OrganizationServiceProxy;
import com.dut.team92.userservice.repository.RedisTokenRepository;
import com.dut.team92.userservice.services.handler.TokenCreateAndSaveHandler;
import com.dut.team92.userservice.services.handler.TokenCreateAndUpdateHandler;
import com.dut.team92.userservice.services.handler.UserCreateCommandHandler;
import com.dut.team92.userservice.services.mapper.UserDataMapper;
import com.dut.team92.userservice.util.TokenUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserCreateCommandHandler userCreateCommandHandler;
    private final UserDataMapper userDataMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenCreateAndSaveHandler tokenCreateAndSaveHandler;
    private final TokenProperties tokenProperties;
    private final TokenProvider tokenProvider;
    private final RedisTokenRepository redisTokenRepository;
    private final TokenCreateAndUpdateHandler tokenCreateAndUpdateHandler;
    private final OrganizationServiceProxy proxy;

    @Override
    public CreateUserResponse signup(CreateUserCommand createUserCommand) {
        UserCreatedEvent userCreatedEvent = userCreateCommandHandler.createUser(createUserCommand);
        return userDataMapper.userToCreateUserResponse(userCreatedEvent.getUser(), "User saved successfully!");
    }

    @Override
    public LoginResponse signin(LoginUserRequest loginUserRequest) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    loginUserRequest.getUsername(),
                    loginUserRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            TokenPair tokenPair = tokenCreateAndSaveHandler.createAndSaveToken(authentication);
            return LoginResponse.builder()
                    .accessToken(tokenPair.getAccessToken())
                    .expiresIn(tokenProperties.getAccessTokenValidityInSeconds())
                    .refreshToken(tokenPair.getRefreshToken())
                    .refreshExpiresIn(tokenProperties.getRefreshTokenValidityInSeconds())
                    .tokenType("Bearer").build();
        } catch (UserNotFoundException exception) {
            throw new UserNotFoundException(exception.getMessage());
        } catch (BadCredentialsException | InternalAuthenticationServiceException badCredentialsException) {
            log.error("AuthenticationError: {}, cause: {}", badCredentialsException.getMessage(),
                    badCredentialsException.getCause());
            throw new UserNotFoundException("Incorrect Username or password!.");
        }
    }

    @Override
    public void validateToken(String authorizationHeader) {
        String accessToken = TokenUtil.extractToken(authorizationHeader);
        tokenProvider.validateToken(accessToken);
        Authentication authentication = tokenProvider.parseAuthentication(accessToken);
        TokenPair tokenPair = redisTokenRepository.read(authentication.getName());

        Optional.ofNullable(tokenPair)
                .map(TokenPair::getAccessToken)
                .filter(accessToken::equals)
                .orElseThrow(InvalidAccessTokenException::new);
    }

    @Override
    public TokenPair refreshToken(RefreshTokenRequest refreshTokenRequest) {
        var refreshToken = refreshTokenRequest.getToken().trim();
        tokenProvider.validateToken(refreshToken);
        Authentication authentication = tokenProvider.parseAuthentication(refreshToken);
        TokenPair currentTokenPair = redisTokenRepository.read(authentication.getName());

        if (currentTokenPair == null || !Objects.equals(refreshToken, currentTokenPair.getRefreshToken()))
            throw InvalidRefreshTokenException.getInstance();

        return tokenCreateAndUpdateHandler.createAndUpdateToken(authentication);
    }

    @Override
    @Transactional
    public CreateUserAdminOrganizationResponse signupOrganization(CreateUserAdminOrganizationCommand
                                                                  createUserAdminOrganizationCommand
    ) {
        UserCreatedEvent userCreatedEvent = userCreateCommandHandler
                .createAdminForOrganization(createUserAdminOrganizationCommand);
        CreateOrganizationCommand createOrganizationCommand = convertUserCommandToCreateOrganizationCommand(
                createUserAdminOrganizationCommand);
        createOrganizationCommand.setUserId(userCreatedEvent.getUser().getId());
        // call api organization-service to create organization
        var response = proxy.createOrganization(createOrganizationCommand);

        if (response.getCode() == null) {
            return userDataMapper.userToCreateUserAdminOrganizationResponse(userCreatedEvent.getUser(),
                    "User saved successfully!", response);
        } else {
            return userCreateCommandHandler.throwExceptionOrganizationService(response);
        }
    }

    private CreateOrganizationCommand convertUserCommandToCreateOrganizationCommand(
            CreateUserAdminOrganizationCommand createUserAdminOrganizationCommand){
        CreateOrganizationCommand command = new CreateOrganizationCommand();
        Objects.requireNonNull(createUserAdminOrganizationCommand);
        BeanUtils.copyProperties(createUserAdminOrganizationCommand, command);
        return command;
    }

    private void fallbackCircuitOpen(CreateOrganizationCommand command, RuntimeException throwable) {

    }
}
