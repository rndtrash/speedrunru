package ru.speedrun.speedrun.services

import ru.speedrun.speedrun.models.Game
import ru.speedrun.speedrun.repositories.GameRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class GameService(private val gameRepository: GameRepository) {
    fun getAllGames(minReleaseDate: LocalDate? = null): List<Game> {
        val games = gameRepository.findAll()
        return if (minReleaseDate != null) {
            games.filter { it.releaseDate?.isAfter(minReleaseDate) == true }
        } else {
            games
        }
    }

    fun getGameById(id: UUID): Game? {
        return gameRepository.findById(id).orElse(null)
    }

    fun createGame(game: Game): Game {
        return gameRepository.save(game)
    }

    fun updateGame(id: UUID, updatedGame: Game): Game? {
        val existingGame = gameRepository.findById(id).get()

        existingGame.name = updatedGame.name
        existingGame.description = updatedGame.description
        existingGame.releaseDate = updatedGame.releaseDate
        existingGame.imageLink = updatedGame.imageLink
        return gameRepository.save(existingGame)
    }

    fun deleteGame(id: UUID) {
        if (!gameRepository.existsById(id)) {
            throw IllegalArgumentException("Game with ID $id not found")
        }
        gameRepository.deleteById(id)
    }
}