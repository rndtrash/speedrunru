package ru.speedrun.speedrun.dto

import ru.speedrun.speedrun.models.Country
import java.util.UUID

//For POST request
data class CountryRequestPostDTO(
    val name: String,
    val flag: String?
)

// For GET request
data class CountryRequestGetDTO(
    val id: UUID,
    val name: String,
    val flag: String?
)

fun Country.toResponseDTO(): CountryRequestGetDTO {
    return CountryRequestGetDTO(
        id = this.id,
        name = this.name,
        flag = this.imageLink
    )
}