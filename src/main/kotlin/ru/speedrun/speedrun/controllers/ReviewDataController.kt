package ru.speedrun.speedrun.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.models.ReviewData
import ru.speedrun.speedrun.dto.reviews.CreateReviewDataDTO
import ru.speedrun.speedrun.dto.reviews.toRequestDTO
import ru.speedrun.speedrun.services.ReviewDataService
import java.util.UUID

@RestController
@RequestMapping("/api/review")
class ReviewDataController(
    private val reviewDataService: ReviewDataService
) {
    @GetMapping
    fun getAllReviews(): ResponseEntity<List<CreateReviewDataDTO>> {
        val reviews = reviewDataService.getAllReviews().map { it.toRequestDTO() }
        return ResponseEntity.ok(reviews)
    }

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: UUID): ResponseEntity<CreateReviewDataDTO> {
        val review = reviewDataService.getReviewById(id)
        return if (review != null) {
            ResponseEntity.ok(review.toRequestDTO())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createReview(@RequestBody request: CreateReviewDataDTO): ResponseEntity<ReviewData> {
        val createdReview = reviewDataService.createReview(request)
        return ResponseEntity.status(201).body(createdReview)
    }

    @PatchMapping("/{id}")
    fun updateReview(@PathVariable id: UUID, @RequestBody request: CreateReviewDataDTO): ResponseEntity<ReviewData> {
        val updatedReview = reviewDataService.updateReview(id, request)
        return ResponseEntity.ok(updatedReview)
    }

    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: UUID): ResponseEntity<Void> {
        reviewDataService.deleteReview(id)
        return ResponseEntity.noContent().build()
    }
}