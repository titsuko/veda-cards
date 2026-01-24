package com.titsuko.server.service

import com.titsuko.server.dto.request.LoginRequest
import com.titsuko.server.dto.request.RegisterRequest
import com.titsuko.server.dto.response.AuthResponse
import com.titsuko.server.model.User
import com.titsuko.server.repository.UserRepository
import com.titsuko.server.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {
    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("User not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        return jwtService.generateTokenResponse(user.email)
    }

    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("User already exists")
        }

        val user = User(
            id = 0,
            email = request.email,
            password = passwordEncoder.encode(request.password).toString(),
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        userRepository.save(user)

        return jwtService.generateTokenResponse(user.email)
    }
}
