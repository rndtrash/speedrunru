package ru.speedrun.speedrun.dto.reviews

import ru.speedrun.speedrun.models.ReviewData
import java.time.LocalDateTime
import java.util.*

data class CreateReviewDataDTO(
    val id: UUID?,
    val speedrunId: UUID,
    val moderatorId: UUID,
    val message: String,
    val date: LocalDateTime
)