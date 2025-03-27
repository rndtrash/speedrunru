package ru.speedrun.speedrun.auth.dto

import ru.speedrun.speedrun.models.RefreshToken

data class ResponseDTO (
    var message: String,
    var token: String,
    val refreshToken: String
)