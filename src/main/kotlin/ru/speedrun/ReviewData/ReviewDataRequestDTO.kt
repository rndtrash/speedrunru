package ru.speedrun.request

import java.time.LocalDateTime
import java.util.UUID

data class ReviewDataRequestDTO(
    val speedrunId: UUID,
    val moderatorId: UUID,
    val message: String,
    val date: LocalDateTime
)