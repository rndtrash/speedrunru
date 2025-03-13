package ru.speedrun.speedrun.services

import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.reviews.CreateReviewDataDTO
import ru.speedrun.speedrun.models.ReviewData
import ru.speedrun.speedrun.repositories.ReviewDataRepository
import ru.speedrun.speedrun.repositories.SpeedrunRepository
import ru.speedrun.speedrun.repositories.UserRepository
import java.util.UUID

@Service
class ReviewDataService(
    private val reviewDataRepository: ReviewDataRepository,
    private val speedrunRepository: SpeedrunRepository,
    private val userRepository: UserRepository
) {
    fun getAllReviews(): List<ReviewData> {
        return reviewDataRepository.findAll()
    }

    fun getReviewById(id: UUID): ReviewData? {
        return reviewDataRepository.findById(id).orElse(null)
    }

    fun createReview(request: CreateReviewDataDTO): ReviewData {
        val speedrun = speedrunRepository.findById(request.speedrunId).get()
        val moderator = userRepository.findById(request.moderatorId).get()
        val review = ReviewData(
            speedrun = speedrun,
            moderator = moderator,
            message = request.message,
            date = request.date
        )
        return reviewDataRepository.save(review)
    }

    fun updateReview(id: UUID, request: CreateReviewDataDTO): ReviewData {
        val existingReview = reviewDataRepository.findById(id).get()
        val speedrun = speedrunRepository.findById(request.speedrunId).get()
        val moderator = userRepository.findById(request.moderatorId).get()

        existingReview.speedrun = speedrun
        existingReview.moderator = moderator
        existingReview.message = request.message
        existingReview.date = request.date
        return reviewDataRepository.save(existingReview)
    }

    fun deleteReview(id: UUID) {
        if (!reviewDataRepository.existsById(id)) {
            throw IllegalArgumentException("Review with ID $id not found")
        }
        reviewDataRepository.deleteById(id)
    }
}