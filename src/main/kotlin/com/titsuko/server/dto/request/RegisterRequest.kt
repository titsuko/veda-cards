package com.titsuko.server.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @NotBlank(message = "Name is required")
    val fullName: String,

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    val email: String,

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password too short")
    val password: String
)
