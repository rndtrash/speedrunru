package ru.speedrun.filters

import jakarta.servlet.ServletException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.speedrun.jwt.JwtUtil
import java.io.IOException

/**
 * Фильтр проверки JWT-токенов.
 *
 * - Проверяет заголовок Authorization.
 * - Валидирует JWT.
 * - Устанавливает пользователя в SecurityContext.
 */
@Component
class JwtAuthFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {


    /**
     * Проверяет JWT-токен перед обработкой запроса.
     *
     * @param request HTTP-запрос.
     * @param response HTTP-ответ.
     * @param filterChain Цепочка фильтров Spring Security.
     */
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: jakarta.servlet.http.HttpServletRequest,
        response: jakarta.servlet.http.HttpServletResponse,
        filterChain: jakarta.servlet.FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)
        val email = jwtUtil.extractEmail(token)


        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(email)

            if (jwtUtil.validateToken(token, userDetails)) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}
