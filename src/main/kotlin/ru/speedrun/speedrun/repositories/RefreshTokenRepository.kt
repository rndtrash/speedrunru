package ru.speedrun.speedrun.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.speedrun.speedrun.models.RefreshToken
import ru.speedrun.speedrun.models.User
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshToken, UUID> {
    fun findByToken(token: String): Optional<RefreshToken>
    fun deleteByUser(user: User)
}
