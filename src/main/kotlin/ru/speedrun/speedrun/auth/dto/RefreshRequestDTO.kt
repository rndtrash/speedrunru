package ru.speedrun.speedrun.auth.dto

import ru.speedrun.speedrun.models.RefreshToken

data class RefreshRequestDTO(
    val refreshToken: String
)
