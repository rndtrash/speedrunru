package ru.speedrun.speedrun.dto.games

import java.time.LocalDate
import java.util.UUID

data class UpdateGameDTO(
    val id: UUID,
    val name: String?,
    val description: String?,
    val releaseDate: LocalDate?,
    val imageLink: String?
)
