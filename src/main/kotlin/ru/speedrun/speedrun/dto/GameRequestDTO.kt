package ru.speedrun.speedrun.dto

import ru.speedrun.speedrun.models.Game
import java.time.LocalDate
import java.util.UUID

data class GameRequestGetDTO(
    val game_id: UUID,
    val game_name: String,
    val game_icon: String?,
    val game_description: String?,
    val game_release_date: LocalDate?,
    val game_categories: List<CategoryRequestGameDTO>
)

data class GameRequestPostDTO(
    val game_name: String,
    val game_icon: String?,
    val game_description: String?,
    val game_release_date: LocalDate?
)

fun Game.toResponseDTO(): GameRequestGetDTO {
    return GameRequestGetDTO(
        game_id = this.id,
        game_name = this.name,
        game_icon = this.imageLink,
        game_description = this.description,
        game_release_date = this.releaseDate,
        game_categories = this.categories?.map { CategoryRequestGameDTO(it.id, it.name) } ?: emptyList()
    )
}