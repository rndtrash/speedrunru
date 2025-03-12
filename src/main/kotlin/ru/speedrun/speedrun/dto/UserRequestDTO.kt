package ru.speedrun.speedrun.dto

import java.time.LocalDate
import java.util.UUID

data class UserRequestDTO(
    val countryId: UUID,
    val name: String,
    val email: String,
    val password: String,
    val regDate: LocalDate,
    val role: String
)