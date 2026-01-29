package com.titsuko.security.filter

import com.titsuko.model.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import tools.jackson.databind.ObjectMapper

@Component
class AdminFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val auth = SecurityContextHolder.getContext().authentication
        val roles = auth?.authorities?.map { it.authority } ?: emptyList()

        if (!roles.contains("ROLE_ADMIN")) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.contentType = "application/json"
            val error = ErrorResponse(
                status = HttpServletResponse.SC_FORBIDDEN,
                message = "User does not have admin role"
            )
            response.writer.write(objectMapper.writeValueAsString(error))
            return
        }

        filterChain.doFilter(request, response)
    }
}
