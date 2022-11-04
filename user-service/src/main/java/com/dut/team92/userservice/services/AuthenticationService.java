package com.dut.team92.userservice.services;

import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.dto.request.LoginUserRequest;
import com.dut.team92.userservice.domain.dto.request.RefreshTokenRequest;
import com.dut.team92.userservice.domain.dto.response.CreateUserResponse;
import com.dut.team92.userservice.domain.dto.response.LoginResponse;
import com.dut.team92.userservice.domain.entity.TokenPair;

public interface AuthenticationService {
    CreateUserResponse signup(CreateUserCommand createUserCommand);
    LoginResponse signin(LoginUserRequest loginUserRequest);
    void validateToken(String authorizationHeader);
    TokenPair refreshToken(RefreshTokenRequest refreshTokenRequest);
}
