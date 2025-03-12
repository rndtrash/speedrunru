package ru.speedrun.speedrun.services

import ru.speedrun.speedrun.models.Category
import ru.speedrun.speedrun.repositories.GameRepository
import ru.speedrun.speedrun.repositories.CategoryRepository
import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.CategoryRequestMainDTO
import java.util.UUID

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val gameRepository: GameRepository
) {
    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }

    fun getCategoryById(id: UUID): Category? {
        return categoryRepository.findById(id).orElse(null)
    }

    fun createCategory(request: CategoryRequestMainDTO): Category {
        val game = gameRepository.findById(request.gameId).get()
        val category = Category(
            game = game,
            name = request.name,
            description = request.description
        )
        return categoryRepository.save(category)
    }

    fun updateCategory(id: UUID, request: CategoryRequestMainDTO): Category {
        val existingCategory = categoryRepository.findById(id).get()
        val game = gameRepository.findById(request.gameId).get()

        existingCategory.name = request.name
        existingCategory.description = request.description
        existingCategory.game = game
        return categoryRepository.save(existingCategory)
    }

    fun deleteCategory(id: UUID) {
        if (!categoryRepository.existsById(id)) {
            throw IllegalArgumentException("Category with ID $id not found")
        }
        categoryRepository.deleteById(id)
    }
}