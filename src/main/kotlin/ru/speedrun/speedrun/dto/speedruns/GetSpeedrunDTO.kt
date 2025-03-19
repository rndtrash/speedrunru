package ru.speedrun.speedrun.dto.speedruns

import ru.speedrun.speedrun.models.Speedrun
import java.time.LocalDateTime
import java.util.*

data class GetSpeedrunDTO(
    val player_name: String,
    val player_country_flag: String?,
    val player_country_name: String,
    val time: Long,
    val submitted_at: LocalDateTime,
    val run_link: String?
)

fun Speedrun.toRequestDTO(): GetSpeedrunDTO {
    return GetSpeedrunDTO(
        player_name = this.author.name,
        player_country_flag = this.author.country.imageLink,
        player_country_name = this.author.country.name,
        time = this.time,
        submitted_at = this.date,
        run_link = this.link
    )
}


data class GetSpeedrunByNewRecordDTO(
    val game_id: UUID,
    val game_name: String,
    val game_icon: String?,
    val user_name: String,
    val country_name: String,
    val country_flag: String?,
    val place: Int,
    val category_name: String,
    val time: Long,
    val created_at: LocalDateTime,
    val run_link: String?
)

fun Speedrun.toRequestSpeedrunByNewRecordDTO(place: Int): GetSpeedrunByNewRecordDTO {
    return GetSpeedrunByNewRecordDTO(
        game_id = this.category.game.id,
        game_name = this.category.game.name,
        game_icon = this.category.game.imageLink,
        user_name = this.author.name,
        country_name = this.author.country.name,
        country_flag = this.author.country.imageLink,
        place = place,
        category_name = this.category.name,
        time = this.time,
        created_at = this.date,
        run_link = this.link
    )
}