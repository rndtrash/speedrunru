package ru.speedrun.service

import ru.speedrun.category.Category
import ru.speedrun.repository.GameRepository
import ru.speedrun.repository.CategoryRepository
import org.springframework.stereotype.Service
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
        val existingCategory = categoryRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Category with ID $id not found")
        return existingCategory
    }

    fun createCategory(category: Category): Category {
        if (category.name.isBlank()) {
            throw IllegalArgumentException("Category name cannot be empty")
        }
        if (category.name.length > 200) {
            throw IllegalArgumentException("Category name cannot exceed 200 characters")
        }

        val game = gameRepository.findById(category.game.id).orElse(null)
            ?: throw IllegalArgumentException("Game with ID ${category.game.id} not found")
        if (categoryRepository.existsByNameAndGameId(category.name, game.id)) {
            throw IllegalArgumentException("Category with name '${category.name}' already exists for game ${game.id}")
        }

        category.game = game
        return categoryRepository.save(category)
    }

    fun updateCategory(id: UUID, updatedCategory: Category): Category? {
        val existingCategory = categoryRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Category with ID $id not found")
        if (updatedCategory.name.isBlank()) {
            throw IllegalArgumentException("Category name cannot be empty")
        }
        if (updatedCategory.name.length > 200) {
            throw IllegalArgumentException("Category name cannot exceed 200 characters")
        }

        val game = gameRepository.findById(updatedCategory.game.id).orElse(null)
            ?: throw IllegalArgumentException("Game with ID ${updatedCategory.game.id} not found")
        if (updatedCategory.name != existingCategory.name &&
            categoryRepository.existsByNameAndGameId(updatedCategory.name, game.id)) {
            throw IllegalArgumentException("Category with name '${updatedCategory.name}' already exists for game ${game.id}")
        }

        existingCategory.name = updatedCategory.name
        existingCategory.description = updatedCategory.description
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