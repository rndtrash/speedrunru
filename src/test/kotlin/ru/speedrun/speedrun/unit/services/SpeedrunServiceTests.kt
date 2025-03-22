package ru.speedrun.speedrun.unit.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import ru.speedrun.speedrun.dto.speedruns.UpdateSpeedrunDTO
import ru.speedrun.speedrun.dto.users.UpdateUserDTO
import ru.speedrun.speedrun.models.*
import ru.speedrun.speedrun.repositories.CategoryRepository
import ru.speedrun.speedrun.repositories.SpeedrunRepository
import ru.speedrun.speedrun.repositories.UserRepository
import ru.speedrun.speedrun.services.SpeedrunService
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
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
}