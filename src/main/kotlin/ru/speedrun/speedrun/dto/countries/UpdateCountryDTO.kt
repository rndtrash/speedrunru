package ru.speedrun.speedrun.dto.countries

import ru.speedrun.speedrun.models.Country
import java.util.*

data class UpdateCountryDTO(
    val id: UUID,
    val name: String,
    val flag: String?
)

fun Country.toRequestDTO(): UpdateCountryDTO {
    return UpdateCountryDTO(
        id = this.id,
        name = this.name,
        flag = this.imageLink
    )
}