package ru.speedrun.speedrun.services

import ru.speedrun.speedrun.models.Game
import ru.speedrun.speedrun.repositories.GameRepository
import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.games.CreateGameDTO
import ru.speedrun.speedrun.dto.games.UpdateGameDTO
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

    fun createGame(gameDTO: CreateGameDTO): Game {
        val game = Game(
            id = UUID.randomUUID(),
            name = gameDTO.name,
            description = gameDTO.description,
            releaseDate = gameDTO.releaseDate,
            imageLink = gameDTO.imageLink
        )
        return gameRepository.save(game)
    }

    fun updateGame(gameDTO: UpdateGameDTO): Game {
        val game = gameRepository.findById(gameDTO.id).get()
        gameDTO.name?.let {game.name = it}
        gameDTO.description?.let { game.description = it }
        gameDTO.releaseDate?.let { game.releaseDate = it }
        gameDTO.imageLink?.let { game.imageLink = it }
        return gameRepository.save(game)
    }

    fun deleteGame(id: UUID) {
        if (!gameRepository.existsById(id)) {
            throw IllegalArgumentException("Game with ID $id not found")
        }
        gameRepository.deleteById(id)
    }
}
