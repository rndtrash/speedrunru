package ru.speedrun.speedrun.controllers

import ru.speedrun.speedrun.models.Category
import ru.speedrun.speedrun.services.CategoryService
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.categories.CreateCategoryDTO
import ru.speedrun.speedrun.dto.categories.UpdateCategoryDTO
import java.util.UUID

@RestController
@RequestMapping("/api/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping
    fun getAllCategories(): List<Category>? = categoryService.getAllCategories()

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: UUID): Category? = categoryService.getCategoryById(id)

    @PostMapping
    fun createCategory(@RequestBody categoryDTO: CreateCategoryDTO): Category  = categoryService.createCategory(categoryDTO)

    @PatchMapping()
    fun updateCategory(@RequestBody categoryDTO: UpdateCategoryDTO): Category = categoryService.updateCategory(categoryDTO)

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: UUID) {
        categoryService.deleteCategory(id)
    }
}
