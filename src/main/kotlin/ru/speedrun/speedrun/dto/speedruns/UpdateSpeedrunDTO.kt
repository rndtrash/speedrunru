package ru.speedrun.speedrun.dto.speedruns

import ru.speedrun.speedrun.models.Speedrun
import java.time.LocalDateTime
import java.util.*

data class UpdateSpeedrunDTO(
    val id: UUID,
    val categoryId: UUID?,
    val authorId: UUID?,
    val date: LocalDateTime?,
    val link: String?,
    val time: Long?,
    val status: String?
)
