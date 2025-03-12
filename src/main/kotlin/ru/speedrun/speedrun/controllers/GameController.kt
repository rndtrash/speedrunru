package ru.speedrun.speedrun.controllers

import ru.speedrun.speedrun.models.Game
import ru.speedrun.speedrun.services.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.GameRequestDTO
import ru.speedrun.speedrun.dto.toResponseDTO
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/api/game")
class GameController(private val gameService: GameService) {
    @GetMapping
    fun getAllGames(@RequestParam(required = false) minReleaseDate: LocalDate?): ResponseEntity<Map<String, List<GameRequestDTO>>> {
        val games = gameService.getAllGames(minReleaseDate)
        val response = mapOf("games" to games.map { it.toResponseDTO() })
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getGameById(@PathVariable id: UUID): ResponseEntity<Map<String, GameRequestDTO?>> {
        val game = gameService.getGameById(id)
        return if (game != null) {
            ResponseEntity.ok(mapOf("game" to game.toResponseDTO()))
        } else {
            ResponseEntity.status(404).body(emptyMap())
        }
    }

    @PostMapping
    fun createGame(@RequestBody game: Game): ResponseEntity<Game> {
        val createdGame = gameService.createGame(game)
        return ResponseEntity.status(201).body(createdGame)
    }

    @PutMapping("/{id}")
    fun updateGame(@PathVariable id: UUID, @RequestBody updatedGame: Game): ResponseEntity<Game> {
        val game = gameService.updateGame(id, updatedGame)
        return ResponseEntity.ok(game)
    }

    @DeleteMapping("/{id}")
    fun deleteGame(@PathVariable id: UUID): ResponseEntity<Void> {
        gameService.deleteGame(id)
        return ResponseEntity.noContent().build()
    }
}