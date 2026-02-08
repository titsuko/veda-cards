package com.veda.server.dto.response;

public record CategoryResponse(
        Integer id,
        String name,
        String slug,
        String description,
        Byte isVisible,
        Integer cardCount
) {
}
