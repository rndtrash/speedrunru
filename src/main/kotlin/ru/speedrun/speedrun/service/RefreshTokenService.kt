package ru.speedrun.speedrun.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.speedrun.speedrun.config.JwtService
import ru.speedrun.speedrun.models.RefreshToken
import ru.speedrun.speedrun.models.User
import ru.speedrun.speedrun.repositories.RefreshTokenRepository
import ru.speedrun.speedrun.repositories.UserRepository
import java.util.*

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    @Value("\${speedrun.jwt.refresh-duration}")
    private var refreshTokenValidity: Long = 30 * 24 * 60 * 1000

    fun createRefreshToken(user: User): RefreshToken =
        refreshTokenRepository.save(
            RefreshToken(
                id = UUID.randomUUID(),
                user = user,
                expiryDate = Date(System.currentTimeMillis() + refreshTokenValidity)
            )
        )

    fun verifyRefreshToken(id: UUID) : RefreshToken {
        val refreshToken = refreshTokenRepository.findById(id)
            .orElseThrow { RuntimeException("Invalid refresh token") }

        if (refreshToken.expiryDate.before(Date())) {
            refreshTokenRepository.delete(refreshToken)
            throw RuntimeException("Refresh token expired")
        }
        return refreshToken
    }

    fun deleteByUser(user: User) {
        refreshTokenRepository.deleteByUser(user)
    }

}
