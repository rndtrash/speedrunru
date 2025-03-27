package ru.speedrun.speedrun.dto.games

import ru.speedrun.speedrun.models.Category
import ru.speedrun.speedrun.models.Game
import java.time.LocalDate
import java.util.UUID

data class GetGameDTO(
    val game_id: UUID,
    val game_name: String,
    val game_icon: String?,
    val game_description: String?,
    val game_release_date: LocalDate?,
    val game_categories: List<CategoryRequestDTO>
)

data class CategoryRequestDTO(
    val category_id: UUID,
    val category_name: String
)

fun Game.toRequestDTO(): GetGameDTO {
    return GetGameDTO(
        game_id = this.id,
        game_name = this.name,
        game_icon = this.imageLink,
        game_description = this.description,
        game_release_date = this.releaseDate,
        game_categories = this.categories?.map { it.toRequestDTO() } ?: emptyList()
    )
}

fun Category.toRequestDTO(): CategoryRequestDTO {
    return CategoryRequestDTO(
        category_id = this.id,
        category_name = this.name
    )
}