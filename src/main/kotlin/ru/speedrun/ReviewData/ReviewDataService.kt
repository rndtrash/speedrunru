package ru.speedrun.service

import org.springframework.stereotype.Service
import ru.speedrun.ReviewData.ReviewData
import ru.speedrun.repository.ReviewDataRepository
import ru.speedrun.repository.SpeedrunRepository
import ru.speedrun.repository.UserRepository
import java.time.LocalDateTime
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
        val existingReviewData = reviewDataRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Review with ID $id not found")
        return existingReviewData
    }

    fun createReview(review: ReviewData): ReviewData {
        if (review.message.isBlank()) throw IllegalArgumentException("Review message cannot be empty")
        if (review.date.isAfter(LocalDateTime.now())) throw IllegalArgumentException("Review date cannot be in the future")
        if (!speedrunRepository.existsById(review.speedrun.id)) throw IllegalArgumentException("Speedrun with ID ${review.speedrun.id} not found")
        if (!userRepository.existsById(review.moderator.id)) throw IllegalArgumentException("Moderator with ID ${review.moderator.id} not found")
        if (reviewDataRepository.existsById(review.speedrun.id)) throw IllegalArgumentException("Review for Speedrun with ID ${review.speedrun.id} already exists")

        return reviewDataRepository.save(review)
    }

    fun updateReview(id: UUID, updatedReview: ReviewData): ReviewData {
        val existingReview = reviewDataRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Review with ID $id not found")
        if (updatedReview.message.isBlank()) throw IllegalArgumentException("Review message cannot be empty")
        if (updatedReview.date.isAfter(LocalDateTime.now())) throw IllegalArgumentException("Review date cannot be in the future")
        if (!speedrunRepository.existsById(updatedReview.speedrun.id)) throw IllegalArgumentException("Speedrun with ID ${updatedReview.speedrun.id} not found")
        if (!userRepository.existsById(updatedReview.moderator.id)) throw IllegalArgumentException("Moderator with ID ${updatedReview.moderator.id} not found")
        if (updatedReview.speedrun.id != id) throw IllegalArgumentException("Cannot change Speedrun ID in review")

        existingReview.moderator = updatedReview.moderator
        existingReview.message = updatedReview.message
        existingReview.date = updatedReview.date
        return reviewDataRepository.save(existingReview)
    }

    fun deleteReview(id: UUID) {
        if (!reviewDataRepository.existsById(id)) {
            throw IllegalArgumentException("Review with ID $id not found")
        }
        reviewDataRepository.deleteById(id)
    }
}