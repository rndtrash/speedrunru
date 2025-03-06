package ru.speedrun.speedrun.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.speedrun.speedrun.models.User
import java.util.Optional
import java.util.UUID

/**
 * Интерфейс для работы с моделями данных пользователей.
 *
 * @author Ivan Abramov
 */
interface UserRepository: JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    fun findByEmail(email: String?): Optional<User>
}
