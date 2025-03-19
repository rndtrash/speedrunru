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

data class CreateSpeedrunByGameidAndByCategiryDTO(
    val player_name: String,
    val category_id: UUID,
    val time: Long,
    val submitted_at: LocalDateTime,
    val run_link: String?
)