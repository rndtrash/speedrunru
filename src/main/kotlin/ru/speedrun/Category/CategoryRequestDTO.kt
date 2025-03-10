package ru.speedrun.request

import java.util.UUID

data class CategoryRequestDTO(
    val name: String,
    val description: String?,
    val gameId: UUID
)