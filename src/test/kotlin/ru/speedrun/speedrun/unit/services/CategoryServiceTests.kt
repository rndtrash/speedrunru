package ru.speedrun.speedrun.unit.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import ru.speedrun.speedrun.dto.categories.CreateCategoryDTO
import ru.speedrun.speedrun.dto.categories.UpdateCategoryDTO
import ru.speedrun.speedrun.models.Category
import ru.speedrun.speedrun.models.Game
import ru.speedrun.speedrun.repositories.CategoryRepository
import ru.speedrun.speedrun.repositories.GameRepository
import ru.speedrun.speedrun.services.CategoryService
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CategoryServiceTests {

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var gameRepository: GameRepository

    @InjectMocks
    private lateinit var categoryService: CategoryService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCategoryByIdThatNotExists() {
        val categoryId = UUID.randomUUID()
        whenever(categoryRepository.findById(categoryId)).thenReturn(Optional.empty())

        val result = categoryService.getCategoryById(categoryId)

        assertNull(result)
    }

    @Test
    fun createCategoryWithGameThatNotExist() {
        val gameId = UUID.randomUUID()
        val createCategoryDTO = CreateCategoryDTO(
            gameId = gameId,
            name = "Стрелялка",
            description = "Описание стрелялки"
        )
        whenever(gameRepository.findById(gameId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            categoryService.createCategory(createCategoryDTO)
        }
    }

    @Test
    fun updateCategoryThatNotExists() {
        val categoryId = UUID.randomUUID()
        val updateCategoryDTO = UpdateCategoryDTO(
            id = categoryId,
            name = "Стрелялочка",
            description = "Описание стрелялочки",
            gameId = UUID.randomUUID()
        )
        whenever(categoryRepository.findById(categoryId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            categoryService.updateCategory(updateCategoryDTO)
        }
    }

    @Test
    fun updateCategoryWithGameThatNotExists() {
        val categoryId = UUID.randomUUID()
        val gameId = UUID.randomUUID()
        val updateCategoryDTO = UpdateCategoryDTO(
            id = categoryId,
            name = "Прохождение игры на эксперте",
            description = "Описание прохождения игры на эксперте",
            gameId = gameId
        )
        val existingCategory = Category(
            id = categoryId,
            name = "Прохождение игры",
            description = "Описание прохождения игры",
            game = Game(
                id = UUID.randomUUID(),
                name = "терарка",
                description = "мир два д",
                releaseDate = LocalDate.of(2025, 3, 20),
                imageLink = "картинка с игроком на фоне мунлорда"
            )
        )
        whenever(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory))
        whenever(gameRepository.findById(gameId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            categoryService.updateCategory(updateCategoryDTO)
        }
    }

    @Test
    fun deleteCategoryThatNotExists() {
        val categoryId = UUID.randomUUID()
        whenever(categoryRepository.existsById(categoryId)).thenReturn(false)

        val exception = assertThrows<IllegalArgumentException> {
            categoryService.deleteCategory(categoryId)
        }
        assertEquals("Category with ID $categoryId not found", exception.message)
    }
}