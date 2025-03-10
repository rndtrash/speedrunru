package ru.speedrun.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.ReviewData.ReviewData
import ru.speedrun.request.ReviewDataRequestDTO
import ru.speedrun.service.ReviewDataService
import ru.speedrun.service.SpeedrunService
import ru.speedrun.service.UserService
import java.util.UUID

@RestController
@RequestMapping("/api/reviews")
class ReviewDataController(
    private val reviewDataService: ReviewDataService,
    private val speedrunService: SpeedrunService,
    private val userService: UserService
) {
    @GetMapping
    fun getAllReviews(): ResponseEntity<List<ReviewData>> {
        val reviews = reviewDataService.getAllReviews()
        return ResponseEntity.ok(reviews)
    }

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: UUID): ResponseEntity<ReviewData> {
        val review = reviewDataService.getReviewById(id)
        return if (review != null) {
            ResponseEntity.ok(review)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createReview(@RequestBody request: ReviewDataRequestDTO): ResponseEntity<ReviewData> {
        val speedrun = speedrunService.getSpeedrunById(request.speedrunId)
            ?: throw IllegalArgumentException("Speedrun with ID ${request.speedrunId} not found")
        val moderator = userService.getUserById(request.moderatorId)
            ?: throw IllegalArgumentException("Moderator with ID ${request.moderatorId} not found")
        val review = ReviewData(
            speedrun = speedrun,
            moderator = moderator,
            message = request.message,
            date = request.date
        )
        val createdReview = reviewDataService.createReview(review)
        return ResponseEntity.status(201).body(createdReview)
    }

    @PutMapping("/{id}")
    fun updateReview(@PathVariable id: UUID, @RequestBody request: ReviewDataRequestDTO): ResponseEntity<ReviewData> {
        val speedrun = speedrunService.getSpeedrunById(request.speedrunId)
            ?: throw IllegalArgumentException("Speedrun with ID ${request.speedrunId} not found")
        val moderator = userService.getUserById(request.moderatorId)
            ?: throw IllegalArgumentException("Moderator with ID ${request.moderatorId} not found")
        val updatedReview = ReviewData(
            speedrun = speedrun,
            moderator = moderator,
            message = request.message,
            date = request.date
        )
        val review = reviewDataService.updateReview(id, updatedReview)
        return ResponseEntity.ok(review)
    }

    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: UUID): ResponseEntity<Void> {
        reviewDataService.deleteReview(id)
        return ResponseEntity.noContent().build()
    }
}