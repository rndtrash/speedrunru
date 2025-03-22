package ru.speedrun.speedrun.unit.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import ru.speedrun.speedrun.dto.speedruns.CreateSpeedrunByGameidAndByCategiryDTO
import ru.speedrun.speedrun.dto.speedruns.CreateSpeedrunDTO
import ru.speedrun.speedrun.dto.speedruns.UpdateSpeedrunDTO
import ru.speedrun.speedrun.models.*
import ru.speedrun.speedrun.repositories.CategoryRepository
import ru.speedrun.speedrun.repositories.SpeedrunRepository
import ru.speedrun.speedrun.repositories.UserRepository
import ru.speedrun.speedrun.services.SpeedrunService
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SpeedrunServiceTests {
    @Mock
    private lateinit var speedrunRepository: SpeedrunRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var speedrunService: SpeedrunService

    val existingSpeedrunId = UUID.randomUUID()
    val existingSpeedrun = Speedrun(
        id = existingSpeedrunId,
        status = Status.PROCESSING,
        date = LocalDateTime.of(2025, 3, 21, 15, 37, 42),
        link = null,
        time = 130000,
        author = User(
            id = UUID.randomUUID(),
            role = Role.ADMIN,
            email = "email",
            name = "name",
            password = "password",
            country = Country(
                id = UUID.randomUUID(),
                name = "countryname",
                null
            ),
            regDate = LocalDate.of(2025, 3, 15)
        ),
        category = Category(
            id = UUID.randomUUID(),
            name = "namecat",
            game = Game(
                id = UUID.randomUUID(),
                name = "gamename",
                null, null, null
            ),
            description = null
        )
    )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getSpeedrunByIdThatNotExists() {
        val speedrunId = UUID.randomUUID()
        whenever(speedrunRepository.findById(speedrunId)).thenReturn(Optional.empty())

        val result = speedrunService.getSpeedrunById(speedrunId)

        assertNull(result)
    }

    @Test
    fun getRunsByGameAndCategoryWhenRunsNotExists() {
        val gameId = UUID.randomUUID()
        val categoryId = UUID.randomUUID()
        whenever(speedrunRepository.findByCategoryId(categoryId)).thenReturn(emptyList())

        val result = speedrunService.getRunsByGameAndCategory(gameId, categoryId)

        assertTrue(result.isEmpty())
    }

    // Тест конечно стрёмный (раздутый), зато наглядный
    @Test
    fun getSpeedrunByNewRecordWithMultipleCategoriesAndRuns() {
        val categoryId1 = UUID.randomUUID()
        val categoryId2 = UUID.randomUUID()
        val categoryId3 = UUID.randomUUID()
        val categoryName1 = "categoryName1"
        val categoryName2 = "categoryName2"
        val categoryName3 = "categoryName3"
        val category1 = TestCategory(categoryId1, categoryName1)
        val category2 = TestCategory(categoryId2, categoryName2)
        val category3 = TestCategory(categoryId3, categoryName3)
        val speedrun1 = TestSpeedrun(category1, time = 1000)
        val speedrun2 = TestSpeedrun(category2, time = 502)
        val speedrun3 = TestSpeedrun(category2, time = 501)
        val speedrun4 = TestSpeedrun(category3, time = 101)
        val speedrun5 = TestSpeedrun(category3, time = 102)
        val speedrun6 = TestSpeedrun(category3, time = 103)
        whenever(speedrunRepository.findTop10ByOrderByDateDesc()).thenReturn(
            listOf(speedrun1, speedrun2, speedrun3, speedrun4, speedrun5, speedrun6)
        )
        whenever(speedrunRepository.findByCategoryId(categoryId1)).thenReturn(
            listOf(speedrun1)
        )
        whenever(speedrunRepository.findByCategoryId(categoryId2)).thenReturn(
            listOf(speedrun2, speedrun3).sortedBy { it.time }
        )
        whenever(speedrunRepository.findByCategoryId(categoryId3)).thenReturn(
            listOf(speedrun4, speedrun5, speedrun6).sortedBy { it.time }
        )

        val result = speedrunService.getSpeedrunByNewRecord()

        assertEquals(6, result.size)
        val resultsByCategory = result.groupBy { it.category_name }

        val category1Results = resultsByCategory[categoryName1]
        assertNotNull(category1Results)
        assertEquals(1, category1Results.size)
        assertEquals(1, category1Results[0].place)
        assertEquals(speedrun1.time, category1Results[0].time)

        val category2Results = resultsByCategory[categoryName2]
        assertNotNull(category2Results)
        assertEquals(2, category2Results.size)
        val sortedCategory2Results = category2Results.sortedBy { it.place }
        assertEquals(speedrun3.time, sortedCategory2Results[0].time)
        assertEquals(speedrun2.time, sortedCategory2Results[1].time)

        val category3Results = resultsByCategory[categoryName3]
        assertNotNull(category3Results)
        assertEquals(3, category3Results.size)
        val sortedCategory3Results = category3Results.sortedBy { it.place }
        assertEquals(speedrun4.time, sortedCategory3Results[0].time)
        assertEquals(speedrun5.time, sortedCategory3Results[1].time)
        assertEquals(speedrun6.time, sortedCategory3Results[2].time)
    }

    @Test
    fun createSpeedrunWithCategoryThatNotExists() {
        val categoryId = UUID.randomUUID()
        val createSpeedrunDTO = CreateSpeedrunDTO(
            categoryId = categoryId,
            authorId = UUID.randomUUID(),
            time = 1000,
            date = LocalDateTime.of(2025, 3, 22, 12, 8, 47),
            status = "processing",
            link = null,
        )
        whenever(categoryRepository.findById(categoryId)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            speedrunService.createSpeedrun(createSpeedrunDTO)
        }
    }

    @Test
    fun createSpeedrunWithAuthorThatNotExists() {
        val categoryId = UUID.randomUUID()
        val category = Category(
            id = categoryId,
            name = "игры на двоих",
            game = Game(
                id = UUID.randomUUID(),
                name = "game name",
                null, null, null, null
            ),
            description = null
        )
        val authorId = UUID.randomUUID()
        val createSpeedrunDTO = CreateSpeedrunDTO(
            categoryId = categoryId,
            authorId = authorId,
            time = 1000,
            date = LocalDateTime.of(2025, 3, 22, 12, 8, 47),
            status = "processing",
            link = null,
        )
        whenever(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category))
        whenever(userRepository.findById(authorId)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            speedrunService.createSpeedrun(createSpeedrunDTO)
        }
    }

    @Test
    fun createSpeedrunWithIncorrectStatus() {
        val categoryId = UUID.randomUUID()
        val category = Category(
            id = categoryId,
            name = "игры на двоих",
            game = Game(
                id = UUID.randomUUID(),
                name = "game name",
                null, null, null, null
            ),
            description = null
        )
        val authorId = UUID.randomUUID()
        val author = User(
            id = authorId,
            name = "username",
            email = "email",
            password = "password",
            country = Country(
                id = UUID.randomUUID(),
                name = "country name",
                imageLink = null
            ),
            role = Role.USER,
            regDate = LocalDate.of(2025, 3, 21)
        )
        val createSpeedrunDTO = CreateSpeedrunDTO(
            categoryId = categoryId,
            authorId = authorId,
            time = 1000,
            date = LocalDateTime.of(2025, 3, 22, 12, 8, 47),
            status = "nonprocessing",
            link = null,
        )
        whenever(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category))
        whenever(userRepository.findById(authorId)).thenReturn(Optional.of(author))

        val exception = assertThrows<IllegalArgumentException> {
            speedrunService.createSpeedrun(createSpeedrunDTO)
        }
    }

    @Test
    fun createSpeedrunByCategoryThatNotExists() {
        val categoryId = UUID.randomUUID()
        val createSpeedrunByGameidAndByCategiryDTO = CreateSpeedrunByGameidAndByCategiryDTO(
            player_name = "player",
            time = 11000,
            category_id = UUID.randomUUID(),
            run_link = null,
            submitted_at = LocalDateTime.of(2025, 3, 22, 11, 33, 13),
        )
        whenever(categoryRepository.findById(categoryId)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            speedrunService.createSpeedrunByGameidAndByCategiry(UUID.randomUUID(), categoryId, createSpeedrunByGameidAndByCategiryDTO)
        }
    }

    @Test
    fun createSpeedrunByAuthorThatNotExists() {
        val categoryId = UUID.randomUUID()
        val category = Category(
            id = categoryId,
            name = "игры на двоих",
            game = Game(
                id = UUID.randomUUID(),
                name = "game name",
                null, null, null, null
            ),
            description = null
        )
        val authorName = "name author"
        val createSpeedrunByGameidAndByCategiryDTO = CreateSpeedrunByGameidAndByCategiryDTO(
            player_name = authorName,
            time = 11000,
            category_id = UUID.randomUUID(),
            run_link = null,
            submitted_at = LocalDateTime.of(2025, 3, 22, 11, 33, 13),
        )
        whenever(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category))
        whenever(userRepository.findByName(authorName)).thenReturn(null)

        val exception = assertThrows<IllegalArgumentException> {
            speedrunService.createSpeedrunByGameidAndByCategiry(UUID.randomUUID(), categoryId, createSpeedrunByGameidAndByCategiryDTO)
        }
        assertEquals("User with name $authorName not found", exception.message)
    }

    @Test
    fun updateSpeedrunThatNotExists() {
        val speedrunId = UUID.randomUUID()
        val updateSpeedrunDTO = UpdateSpeedrunDTO(
            id = speedrunId,
            null, null, null, null, null,
            status = "rejected"
        )
        whenever(speedrunRepository.findById(speedrunId)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            speedrunService.updateSpeedrun(updateSpeedrunDTO)
        }
    }

    @Test
    fun updateSpeedrunWithIncorrectStatus() {
        val updateSpeedrunDTO = UpdateSpeedrunDTO(
            id = existingSpeedrunId,
            null, null, null, null, null,
            status = "newStat"
        )
        whenever(speedrunRepository.findById(existingSpeedrunId)).thenReturn(Optional.of(existingSpeedrun))

        val exception = assertThrows<IllegalArgumentException> {
            speedrunService.updateSpeedrun(updateSpeedrunDTO)
        }
    }

    @Test
    fun updateSpeedrunWithAuthorThatNotExists() {
        val authorId = UUID.randomUUID()
        val updateSpeedrunDTO = UpdateSpeedrunDTO(
            id = existingSpeedrunId,
            null,
            authorId = authorId,
            null, null, null,null
        )
        whenever(speedrunRepository.findById(existingSpeedrunId)).thenReturn(Optional.of(existingSpeedrun))
        whenever(userRepository.findById(authorId)).thenReturn(Optional.empty())
        whenever(speedrunRepository.save(any())).thenAnswer { invocation ->
            invocation.getArgument<Speedrun>(0)
        }

        val exception = assertThrows<NoSuchElementException> {
            speedrunService.updateSpeedrun(updateSpeedrunDTO)
        }
    }

    @Test
    fun updateSpeedrunWithCategoryThatNotExists() {
        val categoryId = UUID.randomUUID()
        val updateSpeedrunDTO = UpdateSpeedrunDTO(
            id = existingSpeedrunId,
            categoryId = categoryId,
            null, null, null, null,null
        )
        whenever(speedrunRepository.findById(existingSpeedrunId)).thenReturn(Optional.of(existingSpeedrun))
        whenever(categoryRepository.findById(categoryId)).thenReturn(Optional.empty())
        whenever(speedrunRepository.save(any())).thenAnswer { invocation ->
            invocation.getArgument<Speedrun>(0)
        }

        val exception = assertThrows<NoSuchElementException> {
            speedrunService.updateSpeedrun(updateSpeedrunDTO)
        }
    }

    @Test
    fun deleteSpeedrunThatNotExists() {
        val speedrunId = UUID.randomUUID()
        whenever(speedrunRepository.existsById(speedrunId)).thenReturn(false)

        val exception = assertThrows<IllegalArgumentException> {
            speedrunService.deleteSpeedrun(speedrunId)
        }
        assertEquals("Speedrun with ID $speedrunId not found", exception.message)
    }

    private fun TestSpeedrun(category: Category, time: Long): Speedrun {
        return Speedrun(
            id = UUID.randomUUID(),
            author = TestAuthor(),
            link = null,
            date = LocalDateTime.of(2025, 3, 20, 12, 40, 12),
            status = Status.PROCESSING,
            category = category,
            time = time
        )
    }

    private fun TestCategory(categoryId: UUID, categoryName: String): Category {
        return Category(
            id = categoryId,
            name = categoryName,
            game = Game(
                id = UUID.randomUUID(),
                imageLink = null,
                name = "gamename",
                description = null,
                categories = null,
                releaseDate = null
            ),
            description = null
        )
    }

    private fun TestAuthor(): User {
        return User(
            id = UUID.randomUUID(),
            name = "username",
            email = "email",
            password = "password",
            role = Role.USER,
            regDate = LocalDate.of(2025, 3, 20),
            country = Country(
                id = UUID.randomUUID(),
                name = "country name",
                imageLink = null
            )
        )
    }
}