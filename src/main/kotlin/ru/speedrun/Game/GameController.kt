package ru.speedrun.controller

import ru.speedrun.Game.Game
import ru.speedrun.service.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/api/games")
class GameController(private val gameService: GameService) {
    @GetMapping
    fun getAllGames(@RequestParam(required = false) minReleaseDate: LocalDate?
    ): ResponseEntity<List<Game>> {
        val games = gameService.getAllGames(minReleaseDate)
        return ResponseEntity.ok(games)
    }

    @GetMapping("/{id}")
    fun getGameById(@PathVariable id: UUID): ResponseEntity<Game> {
        val game = gameService.getGameById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(game)
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