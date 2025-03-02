package ru.speedrun.speedrun.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.speedrun.speedrun.models.User
import java.util.UUID

/**
 * Интерфейс для работы с моделями данных пользователей.
 *
 * @author Ivan Abramov
 */
interface UserRepository: JpaRepository<User, UUID>
