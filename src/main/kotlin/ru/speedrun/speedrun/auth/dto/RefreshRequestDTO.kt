package ru.speedrun.speedrun.auth.dto

import java.util.UUID

data class RefreshRequestDTO(
    val refreshTokenId: UUID
)
