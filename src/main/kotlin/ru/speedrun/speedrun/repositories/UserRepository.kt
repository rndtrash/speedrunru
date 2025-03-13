package ru.speedrun.speedrun.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.speedrun.speedrun.models.User
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByName(name: String): User?
}