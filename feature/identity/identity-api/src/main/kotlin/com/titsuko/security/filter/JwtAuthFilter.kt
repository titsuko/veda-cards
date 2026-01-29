package com.titsuko.security.filter

import com.titsuko.repository.UserRepository
import com.titsuko.security.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)
        val email = jwtService.extractEmail(jwt)

        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            if (jwtService.isTokenValid(jwt)) {
                val user = userRepository.findByEmail(email)
                if (user != null) {
                    val authorities = listOf(
                        org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_${user.role.name}")
                    )

                    val authToken = UsernamePasswordAuthenticationToken(email, null, authorities)
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}