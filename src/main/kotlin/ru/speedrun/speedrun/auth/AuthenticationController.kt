package ru.speedrun.speedrun.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.auth.dto.LoginRequestDTO
import ru.speedrun.speedrun.auth.dto.RefreshRequestDTO
import ru.speedrun.speedrun.auth.dto.RegisterRequestDTO
import ru.speedrun.speedrun.auth.dto.ResponseDTO
import ru.speedrun.speedrun.service.RefreshTokenService

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val refreshTokenService: RefreshTokenService
) {
    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequestDTO
    ): ResponseEntity<ResponseDTO> {
        return ResponseEntity.ok(authenticationService.register(request))
    }

    @PostMapping("/login")
    fun register(
        @RequestBody request: LoginRequestDTO
    ): ResponseEntity<ResponseDTO?> {
        return ResponseEntity.ok(authenticationService.authenticate(request))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequestDTO): ResponseEntity<ResponseDTO> {
       return authenticationService.refresh(request)
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        authenticationService.logout(token)
        return ResponseEntity.ok("User logged out successfully")
    }
}