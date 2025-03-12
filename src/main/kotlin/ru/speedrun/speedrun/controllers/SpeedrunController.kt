package ru.speedrun.speedrun.controllers

import ru.speedrun.speedrun.services.SpeedrunService
import ru.speedrun.speedrun.dto.SpeedrunRequestDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.toRequestDTO
import ru.speedrun.speedrun.models.Speedrun
import java.util.UUID

@RestController
@RequestMapping("/api/speedrun")
class SpeedrunController(
    private val speedrunService: SpeedrunService
) {
    @GetMapping
    fun getAllSpeedruns(): ResponseEntity<List<SpeedrunRequestDTO>> {
        val speedruns = speedrunService.getAllSpeedruns().map { it.toRequestDTO() }
        return ResponseEntity.ok(speedruns)
    }

    @GetMapping("/{id}")
    fun getSpeedrunById(@PathVariable id: UUID): ResponseEntity<SpeedrunRequestDTO> {
        val speedrun = speedrunService.getSpeedrunById(id)
        return if (speedrun != null) {
            ResponseEntity.ok(speedrun.toRequestDTO())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createSpeedrun(@RequestBody request: SpeedrunRequestDTO): ResponseEntity<Speedrun> {
        val createdSpeedrun = speedrunService.createSpeedrun(request)
        return ResponseEntity.status(201).body(createdSpeedrun)
    }

    @PatchMapping("/{id}")
    fun updateSpeedrun(@PathVariable id: UUID, @RequestBody request: SpeedrunRequestDTO): ResponseEntity<Speedrun> {
        val updatedSpeedrun = speedrunService.updateSpeedrun(id, request)
        return ResponseEntity.ok(updatedSpeedrun)
    }

    @DeleteMapping("/{id}")
    fun deleteSpeedrun(@PathVariable id: UUID): ResponseEntity<Void> {
        speedrunService.deleteSpeedrun(id)
        return ResponseEntity.noContent().build()
    }
}