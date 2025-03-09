package ru.speedrun.speedrun.auth.dto

data class RegisterRequestDTO(
    var username: String,
    var email: String,
    var password: String,
)
