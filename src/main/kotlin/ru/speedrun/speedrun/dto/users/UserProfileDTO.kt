package ru.speedrun.speedrun.dto.users

import ru.speedrun.speedrun.models.Country
import ru.speedrun.speedrun.models.Speedrun

data class UserProfileDTO(
    val username: String,
    val avatar: String?,
    val country: Country?,
    val acceptedRuns: List<Speedrun>?,
    val inReviewRuns: List<Speedrun>?,
    val declinedRuns: List<Speedrun>?
)
