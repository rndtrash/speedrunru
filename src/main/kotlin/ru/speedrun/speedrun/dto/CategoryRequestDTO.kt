package ru.speedrun.speedrun.dto

import ru.speedrun.speedrun.models.Category
import java.util.UUID

data class CategoryRequestMainDTO(
    val id: UUID,
    val name: String,
    val description: String?,
    val gameId: UUID
)

// ??? ????? ??? Game
data class CategoryRequestDTO(
    val category_id: UUID,
    val category_name: String
)

fun Category.toRequestDTO(): CategoryRequestMainDTO {
    return CategoryRequestMainDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        gameId = this.game.id
    )
}