package ru.speedrun.speedrun.repositories

import ru.speedrun.speedrun.models.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface GameRepository : JpaRepository<Game, UUID>