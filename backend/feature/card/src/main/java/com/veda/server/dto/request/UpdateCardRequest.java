package com.veda.server.dto.request;

public record UpdateCardRequest(
        String name,
        String description,
        Boolean isVisible,
        Integer categoryId
) {
}
