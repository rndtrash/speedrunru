package ru.speedrun.speedrun.dto.games

import java.time.LocalDate

data class CreateGameDTO(
    val name: String,
    val description: String?,
    val releaseDate: LocalDate?,
    val imageLink: String?
)
