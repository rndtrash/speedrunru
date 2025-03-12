package ru.speedrun.speedrun.dto

import ru.speedrun.speedrun.models.Speedrun
import java.time.LocalDateTime
import java.util.UUID

data class SpeedrunRequestGetDTO(
    val id: UUID,
    val categoryId: UUID,
    val authorId: UUID,
    val date: LocalDateTime,
    val link: String?,
    val time: Long,
    val status: String
)

data class SpeedrunRequestPostDTO(
    val categoryId: UUID,
    val authorId: UUID,
    val date: LocalDateTime,
    val link: String?,
    val time: Long,
    val status: String
)

fun Speedrun.toRequestDTO(): SpeedrunRequestGetDTO {
    return SpeedrunRequestGetDTO(
        id = this.id,
        categoryId = this.category.id,
        authorId = this.author.id,
        date = this.date,
        link = this.link,
        time = this.time,
        status = this.status.toString()
    )
}