package ru.speedrun.request

import java.time.LocalDateTime
import java.util.UUID

data class SpeedrunRequestDTO(
    val categoryId: UUID,
    val authorId: UUID,
    val date: LocalDateTime,
    val link: String?,
    val time: Long,
    val status: String
)