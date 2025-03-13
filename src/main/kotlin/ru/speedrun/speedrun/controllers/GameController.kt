package ru.speedrun.speedrun.controllers

import org.springframework.http.ResponseEntity
import ru.speedrun.speedrun.models.Game
import ru.speedrun.speedrun.services.GameService
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.games.CreateGameDTO
import ru.speedrun.speedrun.dto.games.GetGameDTO
import ru.speedrun.speedrun.dto.games.UpdateGameDTO
import ru.speedrun.speedrun.dto.games.toRequestDTO
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/api/game")
class GameController(private val gameService: GameService) {
    @GetMapping
    fun getAllGames(@RequestParam(required = false) minReleaseDate: LocalDate?): ResponseEntity<Map<String, List<GetGameDTO>>> {
        return try {
            val games = gameService.getAllGames(minReleaseDate).map { it.toRequestDTO() }
            ResponseEntity.ok(mapOf("games" to games))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf())
        }
    }

//    @GetMapping
//    fun getAllGames(@RequestParam(required = false) minReleaseDate: LocalDate?): List<Game> = gameService.getAllGames(minReleaseDate)

    @GetMapping("/{id}")
    fun getGameById(@PathVariable id: UUID): Game? = gameService.getGameById(id)

    @PostMapping
    fun createGame(@RequestBody gameDTO: CreateGameDTO): Game = gameService.createGame(gameDTO)

    @PatchMapping
    fun updateGame(@RequestBody gameDto: UpdateGameDTO): Game = gameService.updateGame(gameDto)

    @DeleteMapping("/{id}")
    fun deleteGame(@PathVariable id: UUID) {
        gameService.deleteGame(id)
    }
}
