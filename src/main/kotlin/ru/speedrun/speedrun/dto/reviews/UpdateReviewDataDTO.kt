package ru.speedrun.speedrun.dto.reviews

import ru.speedrun.speedrun.models.ReviewData
import java.time.LocalDateTime
import java.util.*

data class UpdateReviewDataDTO(
    val id: UUID,
    val speedrunId: UUID?,
    val moderatorId: UUID?,
    val message: String?,
    val date: LocalDateTime?
)

fun ReviewData.toRequestDTO(): UpdateReviewDataDTO {
    return UpdateReviewDataDTO(
        id = this.id,
        speedrunId = this.speedrun.id,
        moderatorId = this.moderator.id,
        message = this.message,
        date = this.date
    )
}