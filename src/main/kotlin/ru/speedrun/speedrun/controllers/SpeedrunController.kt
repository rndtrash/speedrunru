package ru.speedrun.speedrun.controllers

import ru.speedrun.speedrun.services.SpeedrunService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.speedruns.*
import ru.speedrun.speedrun.models.Speedrun
import java.util.UUID

@RestController
@RequestMapping("/api")
class SpeedrunController(
    private val speedrunService: SpeedrunService
) {
    @GetMapping("/speedun")
    fun getAllSpeedruns(): List<Speedrun> = speedrunService.getAllSpeedruns()

    @GetMapping("/speedun/{id}")
    fun getSpeedrunById(@PathVariable id: UUID): Speedrun? = speedrunService.getSpeedrunById(id)

    @GetMapping("/game/{gameId}/category/{categoryId}/run")
    fun getRunsByGameAndCategory(@PathVariable gameId: UUID, @PathVariable categoryId: UUID): ResponseEntity<Map<String, List<GetSpeedrunDTO>>> {
        return try {
            val runs = speedrunService.getRunsByGameAndCategory(gameId, categoryId).map { it.toRequestDTO() }
            ResponseEntity.ok(mapOf("runs" to runs))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(mapOf())
        }
    }

    @GetMapping("/record/latest")
    fun getSpeedrunByNewRecord(): ResponseEntity<Map<String, List<GetSpeedrunByNewRecordDTO>>> {
        return try {
            val runs = speedrunService.getSpeedrunByNewRecord()
            ResponseEntity.ok(mapOf("run_list" to runs))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf())
        }
    }

    @PostMapping("/speedun")
    fun createSpeedrun(@RequestBody request: CreateSpeedrunDTO): Speedrun = speedrunService.createSpeedrun(request)

    @PostMapping("/game/{gameId}/category/{categoryId}/run")
    fun createRun(@PathVariable gameId: UUID, @PathVariable categoryId: UUID, @RequestBody request: CreateSpeedrunByGameidAndByCategiryDTO): ResponseEntity<Map<String, Any>> {
        return try {
            speedrunService.createSpeedrunByGameidAndByCategiry(gameId, categoryId, request)
            ResponseEntity.ok(mapOf())
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf())
        }
    }

    @PatchMapping("/speedun")
    fun updateSpeedrun(@RequestBody request: UpdateSpeedrunDTO): Speedrun = speedrunService.updateSpeedrun(request)

    @DeleteMapping("/speedur/{id}")
    fun deleteSpeedrun(@PathVariable id: UUID) {
        speedrunService.deleteSpeedrun(id)
    }
}