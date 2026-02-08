package com.veda.server.dto.response;

public record CardResponse(
        Integer id,
        String name,
        String description,
        Boolean isVisible,
        CategoryResponse category
) {
}
