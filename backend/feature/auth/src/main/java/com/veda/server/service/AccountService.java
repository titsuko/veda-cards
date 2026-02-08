package com.veda.server.service;

import com.veda.server.HashEncoder;
import com.veda.server.UserRegisteredEvent;
import com.veda.server.dto.request.CheckEmailRequest;
import com.veda.server.dto.request.RegisterRequest;
import com.veda.server.dto.response.AuthResponse;
import com.veda.server.dto.response.AvailabilityResponse;
import com.veda.server.exception.UserAlreadyExists;
import com.veda.server.model.User;
import com.veda.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final HashEncoder encoder;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public AuthResponse createAccount(RegisterRequest request) {
        log.info("Attempting to create account for email: {}", request.email());

        if (userRepository.existsByEmail(request.email())) {
            log.warn("Registration failed: Email {} already exists", request.email());
            throw new UserAlreadyExists("User already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));

        User savedUser = userRepository.save(user);
        log.info("User created successfully for id: {}", savedUser.getId());

        eventPublisher.publishEvent(new UserRegisteredEvent(
                request.fullName(),
                savedUser.getId()
        ));

        return tokenService.issueTokens(savedUser);
    }

    @Transactional(readOnly = true)
    public AvailabilityResponse checkEmail(CheckEmailRequest request) {
        log.debug("Checking email availability for: {}", request.email());
        boolean exists = userRepository.existsByEmail(request.email());
        return new AvailabilityResponse(!exists, exists ? "Email already exists" : null);
    }
}