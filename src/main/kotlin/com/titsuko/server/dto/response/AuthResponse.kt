package com.titsuko.server.dto.response

data class AuthResponse(
    val accessToken: String,
    val expiresIn: Long
)
