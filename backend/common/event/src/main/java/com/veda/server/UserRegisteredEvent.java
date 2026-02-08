package com.veda.server;

public record UserRegisteredEvent(
        String fullName,
        Integer userId) {
}

