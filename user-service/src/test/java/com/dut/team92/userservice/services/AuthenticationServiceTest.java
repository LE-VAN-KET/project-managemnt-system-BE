package com.dut.team92.userservice.services;

import com.dut.team92.userservice.configuration.properties.TokenProperties;
import com.dut.team92.userservice.domain.dto.response.LoginResponse;
import com.dut.team92.userservice.services.handler.TokenCreateAndSaveHandler;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static com.dut.team92.userservice.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenCreateAndSaveHandler tokenCreateAndSaveHandler;

    @Mock
    private TokenProperties tokenProperties;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void signInSuccessWhenUserLoginValid() {

        var authenticationToken = new UsernamePasswordAuthenticationToken(
                VALID_USER_LOGIN.getUsername(),
                VALID_USER_LOGIN.getPassword());

        given(authenticationManager.authenticate(authenticationToken)).willReturn(AUTHENTICATION);
        given(tokenCreateAndSaveHandler.createAndSaveToken(AUTHENTICATION)).willReturn(TOKEN_PAIR);
        given(tokenProperties.getAccessTokenValidityInSeconds())
                .willReturn(TOKEN_PROPERTIES.getAccessTokenValidityInSeconds());
        given(tokenProperties.getRefreshTokenValidityInSeconds())
                .willReturn(TOKEN_PROPERTIES.getRefreshTokenValidityInSeconds());

        LoginResponse loginResponse = authenticationService.signin(VALID_USER_LOGIN);

        assertEquals(loginResponse.getAccessToken(), TOKEN_PAIR.getAccessToken());
        assertEquals(loginResponse.getRefreshToken(), TOKEN_PAIR.getRefreshToken());

        then(authenticationManager).should(times(1)).authenticate(authenticationToken);
        then(tokenCreateAndSaveHandler).should(times(1)).createAndSaveToken(AUTHENTICATION);
        then(tokenProperties).should(times(1)).getAccessTokenValidityInSeconds();
        then(tokenProperties).should(times(1)).getRefreshTokenValidityInSeconds();
    }

//    @Test
//    void signInFailedWhenPasswordInValid() {
//
//        var authenticationToken = new UsernamePasswordAuthenticationToken(
//                VALID_USER_LOGIN.getUsername(),
//                INVALID_USER_LOGIN.getPassword());
//
//        given(authenticationManager.authenticate(authenticationToken))
//                .willThrow(new InternalAuthenticationServiceException("Incorrect Username or password!."));
//
//        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
//                () -> authenticationService.signin(INVALID_USER_LOGIN));
//
//        assertEquals("Incorrect Username or password!.", ex.getMessage());
//
//        then(authenticationManager).should(times(1)).authenticate(authenticationToken);
//    }
}
