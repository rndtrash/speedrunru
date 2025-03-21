package ru.speedrun.speedrun.dto.users

import java.util.UUID

data class UpdateUserProfileDTO(
    val password: String?,
    val avatar: String?,
    val countryId: UUID?
)
