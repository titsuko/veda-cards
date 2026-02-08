package com.veda.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank
        @Size(min = 2, max = 100)
        String name,

        @NotBlank
        @Pattern(regexp = "^[a-z0-9-]+$")
        String slug,

        String description
) {
}
