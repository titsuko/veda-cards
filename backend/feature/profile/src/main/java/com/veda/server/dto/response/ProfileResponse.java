package com.veda.server.dto.response;

import java.time.Instant;

public record ProfileResponse(
        Integer userId,
        String firstName,
        String lastName,
        String bio,
        Instant lastLoginAt
) {
}