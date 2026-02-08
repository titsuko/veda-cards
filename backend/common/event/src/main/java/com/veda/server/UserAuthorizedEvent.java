package com.veda.server;

import java.time.Instant;

public record UserAuthorizedEvent(
        Integer userId,
        Instant authorizedAt
) {
}
