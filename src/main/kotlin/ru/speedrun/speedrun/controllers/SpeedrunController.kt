package ru.speedrun.speedrun.controllers

import ru.speedrun.speedrun.services.SpeedrunService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.categories.CreateCategoryDTO
import ru.speedrun.speedrun.dto.categories.UpdateCategoryDTO
import ru.speedrun.speedrun.dto.games.CreateGameDTO
import ru.speedrun.speedrun.dto.games.UpdateGameDTO
import ru.speedrun.speedrun.dto.speedruns.CreateSpeedrunDTO
import ru.speedrun.speedrun.dto.speedruns.UpdateSpeedrunDTO
import ru.speedrun.speedrun.dto.speedruns.toRequestDTO
import ru.speedrun.speedrun.models.Category
import ru.speedrun.speedrun.models.Game
import ru.speedrun.speedrun.models.Speedrun
import java.time.LocalDate
import java.util.UUID

@RestController
@RequestMapping("/api/speedrun")
class SpeedrunController(
    private val speedrunService: SpeedrunService
) {
    @GetMapping
    fun getAllSpeedruns(): List<Speedrun> = speedrunService.getAllSpeedruns()

    @GetMapping("/{id}")
    fun getSpeedrunById(@PathVariable id: UUID): Speedrun? = speedrunService.getSpeedrunById(id)

    @PostMapping
    fun createSpeedrun(@RequestBody request: CreateSpeedrunDTO): Speedrun = speedrunService.createSpeedrun(request)

    @PatchMapping
    fun updateSpeedrun(@RequestBody request: UpdateSpeedrunDTO): Speedrun = speedrunService.updateSpeedrun(request)

    @DeleteMapping("/{id}")
    fun deleteSpeedrun(@PathVariable id: UUID) {
        speedrunService.deleteSpeedrun(id)
    }
}