package ru.speedrun.speedrun.services

import ru.speedrun.speedrun.repositories.CategoryRepository
import ru.speedrun.speedrun.repositories.SpeedrunRepository
import ru.speedrun.speedrun.models.Speedrun
import ru.speedrun.speedrun.repositories.UserRepository
import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.SpeedrunRequestPostDTO
import ru.speedrun.speedrun.models.Status
import java.util.UUID

@Service
class SpeedrunService(
    private val speedrunRepository: SpeedrunRepository,
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) {
    fun getAllSpeedruns(): List<Speedrun> {
        return speedrunRepository.findAll()
    }

    fun getSpeedrunById(id: UUID): Speedrun? {
        return speedrunRepository.findById(id).orElse(null)
    }

    fun createSpeedrun(request: SpeedrunRequestPostDTO): Speedrun {
        val category = categoryRepository.findById(request.categoryId).get()
        val author = userRepository.findById(request.authorId).get()
        val speedrun = Speedrun(
            id = UUID.randomUUID(),
            category = category,
            author = author,
            date = request.date,
            link = request.link,
            time = request.time,
            status = Status.valueOf(request.status.uppercase())
        )
        return speedrunRepository.save(speedrun)
    }

    fun updateSpeedrun(id: UUID, request: SpeedrunRequestPostDTO): Speedrun {
        val existingSpeedrun = speedrunRepository.findById(id).get()
        val category = categoryRepository.findById(request.categoryId).get()
        val author = userRepository.findById(request.authorId).get()

        existingSpeedrun.category = category
        existingSpeedrun.author = author
        existingSpeedrun.date = request.date
        existingSpeedrun.link = request.link
        existingSpeedrun.time = request.time
        existingSpeedrun.status = Status.valueOf(request.status.uppercase())
        return speedrunRepository.save(existingSpeedrun)
    }

    fun deleteSpeedrun(id: UUID) {
        if (!speedrunRepository.existsById(id)) {
            throw IllegalArgumentException("Speedrun with ID $id not found")
        }
        speedrunRepository.deleteById(id)
    }
}