package ru.speedrun.speedrun.auth.dto

data class AuthenticationRequestDTO (
    var name: String,
    var password: String
)