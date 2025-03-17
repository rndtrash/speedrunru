package ru.speedrun.speedrun.dto.categories

import java.util.*

data class CreateCategoryDTO(
    val name: String,
    val description: String?,
    val gameId: UUID
)
