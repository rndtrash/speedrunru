package ru.speedrun.speedrun.auth.dto

import java.util.UUID

data class RegisterRequestDTO(
    var name: String,
    var email: String,
    var password: String,
    var countryId: UUID
)
