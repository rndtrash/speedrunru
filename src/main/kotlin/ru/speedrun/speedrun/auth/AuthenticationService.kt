package ru.speedrun.speedrun.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.speedrun.speedrun.auth.dto.AuthenticationRequestDTO
import ru.speedrun.speedrun.auth.dto.RegisterRequestDTO
import ru.speedrun.speedrun.auth.dto.ResponseDTO
import ru.speedrun.speedrun.config.JwtService
import ru.speedrun.speedrun.models.Country
import ru.speedrun.speedrun.models.Role
import ru.speedrun.speedrun.models.User
import ru.speedrun.speedrun.repositories.CountryRepository
import ru.speedrun.speedrun.repositories.UserRepository
import java.util.*

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val countryRepository: CountryRepository,
    private val jwtService: JwtService,
    private val manager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
    ) {

    fun register(request: RegisterRequestDTO): ResponseDTO {
        val country = Country(UUID.randomUUID(), "testovo", "none")
        countryRepository.save(country)
        val user = User(
            id = UUID.randomUUID(),
            name = request.name,
            email = request.email,
            userPassword = passwordEncoder.encode(request.password),
            role = Role.USER,
            regDate = Date(),
            country = country
        )
        userRepository.save(user)
        val jwtToken = jwtService.generateToken(user)
        return ResponseDTO(
            token = jwtToken)
    }

    fun authenticate(request: AuthenticationRequestDTO): ResponseDTO {
        manager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.name,
                request.password
            )
        )

        val user: User = userRepository.findByName(request.name).get()
        val jwtToken = jwtService.generateToken(user)
        return ResponseDTO(
            token = jwtToken
        )
    }

}