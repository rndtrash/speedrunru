package ru.speedrun.speedrun.dto

import ru.speedrun.speedrun.models.Country
import java.util.UUID

data class CountryRequestDTO(
    val id: UUID,
    val name: String,
    val flag: String?
)

fun Country.toResponseDTO(): CountryRequestDTO {
    return CountryRequestDTO(
        id = this.id,
        name = this.name,
        flag = this.imageLink
    )
}