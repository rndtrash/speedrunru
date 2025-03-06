package ru.speedrun.dto.user

data class RegisterRequestDTO(
    var name: String,
    var email: String,
    var password: String
)
