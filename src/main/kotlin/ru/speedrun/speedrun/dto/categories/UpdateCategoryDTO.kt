package ru.speedrun.speedrun.dto.categories

import java.util.*

data class UpdateCategoryDTO(
    val id: UUID,
    val name: String?,
    val description: String?,
    val gameId: UUID?
)
