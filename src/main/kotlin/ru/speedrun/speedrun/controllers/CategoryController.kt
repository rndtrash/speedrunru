package ru.speedrun.speedrun.controllers

import ru.speedrun.speedrun.models.Category
import ru.speedrun.speedrun.services.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.CategoryRequestMainDTO
import ru.speedrun.speedrun.dto.toRequestDTO
import java.util.UUID

@RestController
@RequestMapping("/api/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping
    fun getAllCategories(): ResponseEntity<List<CategoryRequestMainDTO>> {
        val categories = categoryService.getAllCategories().map { it.toRequestDTO() }
        return ResponseEntity.ok(categories)
    }

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: UUID): ResponseEntity<CategoryRequestMainDTO> {
        val category = categoryService.getCategoryById(id)
        return if (category != null) {
            ResponseEntity.ok(category.toRequestDTO())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createCategory(@RequestBody request: CategoryRequestMainDTO): ResponseEntity<Category> {
        val createdCategory = categoryService.createCategory(request)
        return ResponseEntity.status(201).body(createdCategory)
    }

    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: UUID, @RequestBody request: CategoryRequestMainDTO): ResponseEntity<Category> {
        val updatedCategory = categoryService.updateCategory(id, request)
        return ResponseEntity.ok(updatedCategory)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: UUID): ResponseEntity<Void> {
        categoryService.deleteCategory(id)
        return ResponseEntity.noContent().build()
    }
}