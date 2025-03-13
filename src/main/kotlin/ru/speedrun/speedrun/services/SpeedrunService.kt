package ru.speedrun.speedrun.services

import ru.speedrun.speedrun.repositories.CategoryRepository
import ru.speedrun.speedrun.repositories.SpeedrunRepository
import ru.speedrun.speedrun.models.Speedrun
import ru.speedrun.speedrun.repositories.UserRepository
import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.speedruns.CreateSpeedrunDTO
import ru.speedrun.speedrun.dto.speedruns.UpdateSpeedrunDTO
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

    fun createSpeedrun(request: CreateSpeedrunDTO): Speedrun {
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

    fun updateSpeedrun(request: UpdateSpeedrunDTO): Speedrun {
        val speedrun = speedrunRepository.findById(request.id).get()
        request.categoryId?.let { categoryId ->
            if (categoryRepository.existsById(categoryId)) {
                speedrun.category = categoryRepository.findById(categoryId).get()
            }
        }
        request.authorId?.let { authorId ->
            if (userRepository.existsById(authorId)) {
                speedrun.author = userRepository.findById(authorId).get()
            }
        }
        request.date?.let { speedrun.date = it }
        request.link?.let { speedrun.link = it }
        request.time?.let { speedrun.time = it }
        request.status?.let { speedrun.status = Status.valueOf(it.uppercase()) }
        return speedrunRepository.save(speedrun)
    }

    fun deleteSpeedrun(id: UUID) {
        if (!speedrunRepository.existsById(id)) {
            throw IllegalArgumentException("Speedrun with ID $id not found")
        }
        speedrunRepository.deleteById(id)
    }
}