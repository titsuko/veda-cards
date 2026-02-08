package com.veda.server.dto.request;

public record UpdateCategoryRequest(
        String name,
        String slug,
        String description,
        Boolean isVisible
) {
}
