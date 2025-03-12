package ru.speedrun.speedrun.dto

import ru.speedrun.speedrun.models.Category
import java.util.UUID

// For POST request
data class CategoryRequestPostDTO(
    val name: String,
    val description: String?,
    val gameId: UUID
)

// Hand for Game
data class CategoryRequestGameDTO(
    val category_id: UUID,
    val category_name: String
)

// For GET request
data class CategoryRequestDTO(
    val id: UUID,
    val name: String,
    val description: String?,
    val gameId: UUID
)

fun Category.toRequestDTO(): CategoryRequestDTO {
    return CategoryRequestDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        gameId = this.game.id
    )
}