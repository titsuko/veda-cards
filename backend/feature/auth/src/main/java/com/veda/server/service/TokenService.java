package com.veda.server.service;

import com.veda.server.JwtService;
import com.veda.server.dto.response.AuthResponse;
import com.veda.server.exception.InvalidTokenException;
import com.veda.server.model.Token;
import com.veda.server.model.User;
import com.veda.server.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse issueTokens(User user) {
        log.info("Issuing new token pair for user {}", user.getId());
        String accessToken = jwtService.generateAccessToken(user.getId().toString());
        String refreshToken = jwtService.generateRefreshToken(user.getId().toString());

        saveRefreshToken(user, refreshToken);

        return new AuthResponse(refreshToken, accessToken);
    }

    @Transactional
    public AuthResponse refreshTokens(String oldRefreshToken) {
        log.debug("Attempting to refresh token using provided refresh token");

        if (!jwtService.validateRefreshToken(oldRefreshToken)) {
            log.warn("Refresh token validation failed");
            throw new InvalidTokenException("Invalid refresh token signature");
        }

        Token storedToken = tokenRepository.findByToken(oldRefreshToken)
                .orElseThrow(() -> {
                    log.warn("Refresh token not found in database");
                    return new InvalidTokenException("Token not found");
                });

        if (storedToken.getIsRevoked() == 1) {
            log.warn("Attempt to use revoked token by user ID: {}", storedToken.getUser().getId());
            throw new InvalidTokenException("Token has been revoked");
        }

        revokeToken(storedToken);

        return issueTokens(storedToken.getUser());
    }

    private void saveRefreshToken(User user, String tokenValue) {
        log.trace("Saving new refresh token for user: {}", user.getId());

        Token token = new Token();
        token.setUser(user);
        token.setToken(tokenValue);
        token.setIsRevoked((byte) 0);
        token.setExpiresIn(java.time.Instant.now().plusMillis(jwtService.getRefreshTokenValidityMs()));

        tokenRepository.save(token);
    }

    public void revokeToken(Token token) {
        log.debug("Revoking token ID: {} for user: {}", token.getId(), token.getUser().getId());token.setIsRevoked((byte) 1);
        tokenRepository.save(token);
    }
}