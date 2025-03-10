package ru.speedrun.service

import ru.speedrun.repository.CategoryRepository
import ru.speedrun.repository.SpeedrunRepository
import ru.speedrun.Speedrun.Speedrun
import ru.speedrun.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SpeedrunService(
    private val speedrunRepository: SpeedrunRepository,
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) {
    private val validStatuses = setOf("verified", "rejected", "processing", "Verified", "Rejected", "Processing")
    fun getAllSpeedruns(): List<Speedrun> {
        return speedrunRepository.findAll()
    }

    fun getSpeedrunById(id: UUID): Speedrun? {
        val existingSpeedrun = speedrunRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Speedrun with ID $id not found")
        return existingSpeedrun
    }

    fun createSpeedrun(speedrun: Speedrun): Speedrun {
        if (speedrun.date == null) throw IllegalArgumentException("Speedrun date cannot be null")
        if (speedrun.time <= 0) throw IllegalArgumentException("Speedrun time must be positive")
        if (speedrun.status.isBlank()) throw IllegalArgumentException("Speedrun status cannot be empty")
        if (speedrun.status !in validStatuses) throw IllegalArgumentException("Speedrun status must be one of: $validStatuses")
        if (speedrun.status.length > 100) throw IllegalArgumentException("Speedrun status cannot exceed 100 characters")
        if (speedrun.link?.length ?: 0 > 255) throw IllegalArgumentException("Speedrun link cannot exceed 255 characters")

        val category = categoryRepository.findById(speedrun.category.id).orElse(null)
            ?: throw IllegalArgumentException("Category with ID ${speedrun.category.id} not found")
        val author = userRepository.findById(speedrun.author.id).orElse(null)
            ?: throw IllegalArgumentException("Author with ID ${speedrun.author.id} not found")
        if (speedrunRepository.existsByAuthorIdAndCategoryIdAndTime(speedrun.author.id, category.id, speedrun.time)) {
            throw IllegalArgumentException("Speedrun with author ${speedrun.author.id}, category ${category.id}, and time ${speedrun.time} already exists")
        }

        speedrun.category = category
        speedrun.author = author
        return speedrunRepository.save(speedrun)
    }

    fun updateSpeedrun(id: UUID, updatedSpeedrun: Speedrun): Speedrun {
        val existingSpeedrun = speedrunRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Speedrun with ID $id not found")
        if (updatedSpeedrun.date == null) throw IllegalArgumentException("Speedrun date cannot be null")
        if (updatedSpeedrun.time <= 0) throw IllegalArgumentException("Speedrun time must be positive")
        if (updatedSpeedrun.status.isBlank()) throw IllegalArgumentException("Speedrun status cannot be empty")
        if (updatedSpeedrun.status !in validStatuses) throw IllegalArgumentException("Speedrun status must be one of: $validStatuses")
        if (updatedSpeedrun.status.length > 100) throw IllegalArgumentException("Speedrun status cannot exceed 100 characters")
        if (updatedSpeedrun.link?.length ?: 0 > 255) throw IllegalArgumentException("Speedrun link cannot exceed 255 characters")

        val category = categoryRepository.findById(updatedSpeedrun.category.id).orElse(null)
            ?: throw IllegalArgumentException("Category with ID ${updatedSpeedrun.category.id} not found")
        val author = userRepository.findById(updatedSpeedrun.author.id).orElse(null)
            ?: throw IllegalArgumentException("Author with ID ${updatedSpeedrun.author.id} not found")
        if ((updatedSpeedrun.author.id != existingSpeedrun.author.id ||
                    updatedSpeedrun.category.id != existingSpeedrun.category.id ||
                    updatedSpeedrun.time != existingSpeedrun.time) &&
            speedrunRepository.existsByAuthorIdAndCategoryIdAndTime(updatedSpeedrun.author.id, category.id, updatedSpeedrun.time)) {
            throw IllegalArgumentException("Speedrun with author ${updatedSpeedrun.author.id}, category ${category.id}, and time ${updatedSpeedrun.time} already exists")
        }

        existingSpeedrun.category = category
        existingSpeedrun.author = author
        existingSpeedrun.date = updatedSpeedrun.date
        existingSpeedrun.link = updatedSpeedrun.link
        existingSpeedrun.time = updatedSpeedrun.time
        existingSpeedrun.status = updatedSpeedrun.status
        return speedrunRepository.save(existingSpeedrun)
    }

    fun deleteSpeedrun(id: UUID) {
        if (!speedrunRepository.existsById(id)) {
            throw IllegalArgumentException("Speedrun with ID $id not found")
        }
        speedrunRepository.deleteById(id)
    }
}