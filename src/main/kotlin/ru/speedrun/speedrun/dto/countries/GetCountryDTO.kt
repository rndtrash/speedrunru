package ru.speedrun.speedrun.dto.countries

import ru.speedrun.speedrun.models.Country
import java.util.UUID

data class GetCountryDTO(
    val id: UUID,
    val name: String,
    val flag: String?
)

fun Country.toRequestDTO(): GetCountryDTO {
    return GetCountryDTO(
        id = this.id,
        name = this.name,
        flag = this.imageLink
    )
}