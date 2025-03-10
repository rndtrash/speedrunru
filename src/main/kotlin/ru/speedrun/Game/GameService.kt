package ru.speedrun.service

import ru.speedrun.Game.Game
import ru.speedrun.repository.GameRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class GameService(private val gameRepository: GameRepository) {
    fun getAllGames(minReleaseDate: LocalDate? = null): List<Game> {
        return gameRepository.findAll()
    }

    fun getGameById(id: UUID): Game? {
        val existingGame = gameRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Game with ID $id not found")
        return existingGame
    }

    fun createGame(game: Game): Game {
        if (game.name.isBlank()) {
            throw IllegalArgumentException("Game name cannot be empty")
        }
        if (game.name.length > 200) {
            throw IllegalArgumentException("Game name cannot exceed 200 characters")
        }
        if (gameRepository.existsByName(game.name)) {
            throw IllegalArgumentException("Game with name '${game.name}' already exists")
        }
        return gameRepository.save(game)
    }

    fun updateGame(id: UUID, updatedGame: Game): Game? {
        val existingGame = gameRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Game with ID $id not found")
        if (updatedGame.name.isBlank()) {
            throw IllegalArgumentException("Game name cannot be empty")
        }
        if (updatedGame.name.length > 200) {
            throw IllegalArgumentException("Game name cannot exceed 200 characters")
        }
        if (updatedGame.name != existingGame.name && gameRepository.existsByName(updatedGame.name)) {
            throw IllegalArgumentException("Game with name '${updatedGame.name}' already exists")
        }

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