package ru.speedrun.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.speedrun.User.User
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun existsByEmail(email: String): Boolean
}