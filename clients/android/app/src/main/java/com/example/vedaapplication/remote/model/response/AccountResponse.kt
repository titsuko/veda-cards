package com.example.vedaapplication.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    val firstName: String,
    val lastName: String,
    val role: String,
    val createdAt: String?
)
