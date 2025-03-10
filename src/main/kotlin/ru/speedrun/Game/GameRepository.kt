package ru.speedrun.repository

import ru.speedrun.Game.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface GameRepository : JpaRepository<Game, UUID> {
    fun existsByName(name: String): Boolean
}