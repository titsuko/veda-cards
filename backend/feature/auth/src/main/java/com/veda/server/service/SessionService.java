package com.veda.server.service;

import com.veda.server.HashEncoder;
import com.veda.server.UserAuthorizedEvent;
import com.veda.server.dto.request.LoginRequest;
import com.veda.server.dto.request.RefreshTokenRequest;
import com.veda.server.dto.response.AuthResponse;
import com.veda.server.exception.InvalidCredentialsException;
import com.veda.server.model.User;
import com.veda.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;
    private final HashEncoder encoder;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public AuthResponse loginUser(LoginRequest request) {
        log.info("Login attempt for email: {}", request.email());

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.warn("Login failed: User with email: {} not found", request.email());
                    return new InvalidCredentialsException();
                });

        if (!encoder.matches(request.password(), user.getPassword())) {
            log.warn("Login failed: Incorrect password for user: {}", request.email());
            throw new InvalidCredentialsException();
        }

        log.info("User {} logged in successfully", user.getId());
        eventPublisher.publishEvent(new UserAuthorizedEvent(
                user.getId(),
                Instant.now()
        ));

        return tokenService.issueTokens(user);
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        log.debug("SessionService delegating token refresh");
        return tokenService.refreshTokens(request.token());
    }
}