package com.dut.team92.userservice.services.handler;

import com.dut.team92.userservice.domain.entity.TokenPair;
import com.dut.team92.userservice.repository.RedisTokenRepository;
import com.dut.team92.userservice.security.jwt.TokenCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCreateAndSaveHandler {
    private final RedisTokenRepository redisTokenRepository;
    private final TokenCreator tokenCreator;

    public TokenPair createAndSaveToken(Authentication authentication) {
        TokenPair tokenPair = tokenCreator.createTokenPair(authentication);
        redisTokenRepository.save(authentication.getName(), tokenPair);
        return tokenPair;
    }
}
