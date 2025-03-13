package ru.speedrun.speedrun.controllers

import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.models.ReviewData
import ru.speedrun.speedrun.dto.reviews.CreateReviewDataDTO
import ru.speedrun.speedrun.dto.reviews.UpdateReviewDataDTO
import ru.speedrun.speedrun.services.ReviewDataService
import java.util.UUID

@RestController
@RequestMapping("/api/review")
class ReviewDataController(
    private val reviewDataService: ReviewDataService
) {
    @GetMapping
    fun getAllReviews(): List<ReviewData>? = reviewDataService.getAllReviews()

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: UUID): ReviewData? = reviewDataService.getReviewById(id)

    @PostMapping
    fun createReview(@RequestBody request: CreateReviewDataDTO): ReviewData = reviewDataService.createReview(request)

    @PatchMapping()
    fun updateReview(@RequestBody request: UpdateReviewDataDTO): ReviewData = reviewDataService.updateReview(request)

    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: UUID) {
        reviewDataService.deleteReview(id)
    }
}
