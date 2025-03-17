package ru.speedrun.speedrun.services

import ru.speedrun.speedrun.models.Category
import ru.speedrun.speedrun.repositories.GameRepository
import ru.speedrun.speedrun.repositories.CategoryRepository
import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.categories.CreateCategoryDTO
import ru.speedrun.speedrun.dto.categories.UpdateCategoryDTO
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

    fun createCategory(request: CreateCategoryDTO): Category {
        val game = gameRepository.findById(request.gameId).get()
        val category = Category(
            game = game,
            name = request.name,
            description = request.description
        )
        return categoryRepository.save(category)
    }

    fun updateCategory(request: UpdateCategoryDTO): Category {
        val category = categoryRepository.findById(request.id).get()
        val game = gameRepository.findById(request.gameId!!).get()
        request.name?.let { category.name = it }
        request.description?.let { category.description = it }
        if (gameRepository.existsById(request.gameId)) {
            category.game = gameRepository.findById(request.gameId).get()
        }
        return categoryRepository.save(category)
    }

    fun deleteCategory(id: UUID) {
        if (!categoryRepository.existsById(id)) {
            throw IllegalArgumentException("Category with ID $id not found")
        }
        categoryRepository.deleteById(id)
    }
}
