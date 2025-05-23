package ru.speedrun.speedrun.dto.users

import java.time.LocalDate
import java.util.*

data class UpdateUserDTO(
    val id: UUID,
    val countryId: UUID?,
    val name: String?,
    val email: String?,
    val password: String?,
    val regDate: LocalDate?,
    val role: String?
)