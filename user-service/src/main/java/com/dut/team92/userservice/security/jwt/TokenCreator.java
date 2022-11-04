package com.dut.team92.userservice.security.jwt;

import com.dut.team92.common.enums.TokenType;
import com.dut.team92.common.security.TokenKey;
import com.dut.team92.common.security.TokenProvider;
import com.dut.team92.common.security.model.CustomUserPrincipal;
import com.dut.team92.userservice.configuration.properties.TokenProperties;
import com.dut.team92.userservice.domain.entity.TokenPair;
import com.dut.team92.userservice.util.FormatterUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


/*
* @author: levanket
* @since: 01/11/2022 22:06
* @description: Class provide generate and validate sign token
* @update:
**/
@Slf4j
@Component
public class TokenCreator {
    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private TokenProvider tokenProvider;

    /**
     * Generating a JWT
     * @param authentication Authentication
     * @param tokenType token type(ACCESS OR REFRESH TOKEN)
     * @return jwt
     * */
    private String generateToken(Authentication authentication, TokenType tokenType) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();

        Long tokenValidityInSeconds = (tokenType == TokenType.ACCESS) ?
                tokenProperties.getAccessTokenValidityInSeconds() : tokenProperties.getRefreshTokenValidityInSeconds();

        LocalDateTime validity = LocalDateTime.now().plusSeconds(tokenValidityInSeconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(TokenKey.AUTHORITIES, authorities)
                .claim(TokenKey.TOKEN_TYPE, tokenType)
                .claim(TokenKey.FULL_NAME, customUserPrincipal.getFullName())
                .claim(TokenKey.SUB_ID, customUserPrincipal.getSubId())
                .signWith(SignatureAlgorithm.RS256, tokenProvider.getPrivateKey())
                .setExpiration(FormatterUtil.convertToUtilDate(validity))
                .compact();
    }

    public TokenPair createTokenPair(Authentication authentication) {
        return TokenPair.builder()
                .accessToken(generateToken(authentication, TokenType.ACCESS))
                .refreshToken(generateToken(authentication, TokenType.REFRESH)).build();
    }

}
