package ru.speedrun.speedrun.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.speedrun.speedrun.auth.dto.AuthenticationRequestDTO
import ru.speedrun.speedrun.auth.dto.RegisterRequestDTO
import ru.speedrun.speedrun.auth.dto.ResponseDTO

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequestDTO
    ): ResponseEntity<ResponseDTO> {
        return ResponseEntity.ok(authenticationService.register(request))
    }

    @PostMapping("/authenticate")
    fun register(
        @RequestBody request: AuthenticationRequestDTO
    ): ResponseEntity<ResponseDTO?> {
        return ResponseEntity.ok(authenticationService.authenticate(request))
    }
}