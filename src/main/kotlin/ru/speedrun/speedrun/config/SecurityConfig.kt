package ru.speedrun.speedrun.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationProvider: AuthenticationProvider
) {

    private val WHITE_LIST_URL = arrayOf(
        "/**" // похуй, пляшем
        // "/",
        // "/games/*",
        // "/login/*",
        // "/registration/*",
        // "/api/auth/**",
        // "/v2/api-docs",
        // "/v3/api-docs",
        // "/v3/api-docs/**",
        // "/swagger-resources",
        // "/swagger-resources/**",
        // "/configuration/ui",
        // "/configuration/security",
        // "/swagger-ui/**",
        // "/webjars/**",
        // "/swagger-ui.html"
    )

    private val ADMIN_LIST_POST = arrayOf(
        "/api/user",
        "/api/speedrun",
        "/api/review",
        "/api/game",
        "/api/country",
        "/api/category"
    )

    private val ADMIN_LIST_PATCH = arrayOf(
        "/api/review",
        "/api/game",
        "/api/country",
        "/api/category"
    )

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {it.disable()}
            .authorizeHttpRequests {
                it
                    .requestMatchers(*WHITE_LIST_URL).permitAll()
                    .requestMatchers("/api/demo/admin/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, *ADMIN_LIST_POST).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, *ADMIN_LIST_PATCH).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST,"/api/review").hasRole("MODERATOR")
                    .requestMatchers(HttpMethod.PATCH,"/api/review").hasRole("MODERATOR")
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
