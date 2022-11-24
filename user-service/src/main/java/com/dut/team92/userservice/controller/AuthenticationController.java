package com.dut.team92.userservice.controller;

import com.dut.team92.userservice.domain.dto.request.CreateUserAdminOrganizationCommand;
import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.dto.request.LoginUserRequest;
import com.dut.team92.userservice.domain.dto.request.RefreshTokenRequest;
import com.dut.team92.userservice.domain.dto.response.CreateUserAdminOrganizationResponse;
import com.dut.team92.userservice.domain.dto.response.CreateUserResponse;
import com.dut.team92.userservice.domain.dto.response.LoginResponse;
import com.dut.team92.userservice.domain.entity.TokenPair;
import com.dut.team92.userservice.services.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@CrossOrigin(origins = { "https://8c01-2402-800-6205-3bfb-ed1c-5ffd-35f2-b461.ap.ngrok.io",
        "http://localhost:8080" }, maxAge = 4800, allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> signup(@Valid @RequestBody CreateUserCommand createUserCommand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signup(createUserCommand));
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        return ResponseEntity.ok().body(authenticationService.signin(loginUserRequest));
    }

    @GetMapping("/validate/token")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<?> verify(@RequestHeader("Authorization") @NotNull @NotBlank String authorizationHeader) {
        authenticationService.validateToken(authorizationHeader);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenPair> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/signup/organization")
    public ResponseEntity<CreateUserAdminOrganizationResponse> signupOrganization(
            @Valid @RequestBody CreateUserAdminOrganizationCommand createUserAdminOrganizationCommand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService
                .signupOrganization(createUserAdminOrganizationCommand));
    }

}
