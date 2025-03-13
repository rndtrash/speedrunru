package ru.speedrun.speedrun.dto.speedruns

import java.time.LocalDateTime
import java.util.*

data class CreateSpeedrunDTO(
    val categoryId: UUID,
    val authorId: UUID,
    val date: LocalDateTime,
    val link: String?,
    val time: Long,
    val status: String
)