package com.veda.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardRequest(
        @NotBlank
        String name,

        String description,

        @NotNull
        Integer categoryId
) {
}
