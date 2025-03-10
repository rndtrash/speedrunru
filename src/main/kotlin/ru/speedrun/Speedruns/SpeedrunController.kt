package ru.speedrun.controller

import ru.speedrun.service.CategoryService
import ru.speedrun.service.SpeedrunService
import ru.speedrun.request.SpeedrunRequestDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.Speedrun.Speedrun
import ru.speedrun.service.UserService
import java.util.UUID

@RestController
@RequestMapping("/api/speedruns")
class SpeedrunController(
    private val speedrunService: SpeedrunService,
    private val categoryService: CategoryService,
    private val userService: UserService
) {
    @GetMapping
    fun getAllSpeedruns(): ResponseEntity<List<Speedrun>> {
        val speedruns = speedrunService.getAllSpeedruns()
        return ResponseEntity.ok(speedruns)
    }

    @GetMapping("/{id}")
    fun getSpeedrunById(@PathVariable id: UUID): ResponseEntity<Speedrun> {
        val speedrun = speedrunService.getSpeedrunById(id)
        return if (speedrun != null) {
            ResponseEntity.ok(speedrun)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createSpeedrun(@RequestBody request: SpeedrunRequestDTO): ResponseEntity<Speedrun> {
        val category = categoryService.getCategoryById(request.categoryId)
            ?: throw IllegalArgumentException("Category with ID ${request.categoryId} not found")
        val author = userService.getUserById(request.authorId)
            ?: throw IllegalArgumentException("Author with ID ${request.authorId} not found")
        val speedrun = Speedrun(
            category = category,
            author = author,
            date = request.date,
            link = request.link,
            time = request.time,
            status = request.status
        )
        val createdSpeedrun = speedrunService.createSpeedrun(speedrun)
        return ResponseEntity.status(201).body(createdSpeedrun)
    }

    @PutMapping("/{id}")
    fun updateSpeedrun(@PathVariable id: UUID, @RequestBody request: SpeedrunRequestDTO): ResponseEntity<Speedrun> {
        val category = categoryService.getCategoryById(request.categoryId)
            ?: throw IllegalArgumentException("Category with ID ${request.categoryId} not found")
        val author = userService.getUserById(request.authorId)
            ?: throw IllegalArgumentException("Author with ID ${request.authorId} not found")
        val updatedSpeedrun = Speedrun(
            category = category,
            author = author,
            date = request.date,
            link = request.link,
            time = request.time,
            status = request.status
        )
        val speedrun = speedrunService.updateSpeedrun(id, updatedSpeedrun)
        return ResponseEntity.ok(speedrun)
    }

    @DeleteMapping("/{id}")
    fun deleteSpeedrun(@PathVariable id: UUID): ResponseEntity<Void> {
        speedrunService.deleteSpeedrun(id)
        return ResponseEntity.noContent().build()
    }
}