package ru.speedrun.speedrun.dto

import ru.speedrun.speedrun.models.ReviewData
import java.time.LocalDateTime
import java.util.UUID

data class ReviewDataRequestDTO(
    val id: UUID?,
    val speedrunId: UUID,
    val moderatorId: UUID,
    val message: String,
    val date: LocalDateTime
)

fun ReviewData.toRequestDTO(): ReviewDataRequestDTO {
    return ReviewDataRequestDTO(
        id = this.id,
        speedrunId = this.speedrun.id,
        moderatorId = this.moderator.id,
        message = this.message,
        date = this.date
    )
}