package ru.speedrun.speedrun.unit.services


import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import ru.speedrun.speedrun.dto.reviews.CreateReviewDataDTO
import ru.speedrun.speedrun.dto.reviews.UpdateReviewDataDTO
import ru.speedrun.speedrun.dto.speedruns.CreateSpeedrunByGameidAndByCategiryDTO
import ru.speedrun.speedrun.dto.speedruns.CreateSpeedrunDTO
import ru.speedrun.speedrun.dto.speedruns.UpdateSpeedrunDTO
import ru.speedrun.speedrun.models.*
import ru.speedrun.speedrun.repositories.CategoryRepository
import ru.speedrun.speedrun.repositories.ReviewDataRepository
import ru.speedrun.speedrun.repositories.SpeedrunRepository
import ru.speedrun.speedrun.repositories.UserRepository
import ru.speedrun.speedrun.services.ReviewDataService
import ru.speedrun.speedrun.services.SpeedrunService
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ReviewDataServiceTests {
    @Mock
    private lateinit var reviewDataRepository: ReviewDataRepository

    @Mock
    private lateinit var speedrunRepository: SpeedrunRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var reviewDataService: ReviewDataService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getReviewByIdThatNotExists() {
        val reviewDataId = UUID.randomUUID()
        whenever(reviewDataRepository.findById(reviewDataId)).thenReturn(Optional.empty())

        val result = reviewDataService.getReviewById(reviewDataId)

        assertNull(result)
    }

    @Test
    fun createSpeedrunWithSpeedrunThatNotExists() {
        val speedrunId = UUID.randomUUID()
        val createReviewDataDTO = CreateReviewDataDTO(
            id = UUID.randomUUID(),
            date = LocalDateTime.of(2025, 3, 22, 13, 24, 24),
            speedrunId = speedrunId,
            message = "message",
            moderatorId = UUID.randomUUID()
        )
        whenever(speedrunRepository.findById(speedrunId)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            reviewDataService.createReview(createReviewDataDTO)
        }
    }

    @Test
    fun createReviewWithModeratorThatNotExists() {
        val speedrunId = UUID.randomUUID()
        val speedrun = Speedrun(
            id = speedrunId,
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
        val moderatorId = UUID.randomUUID()
        val createReviewDataDTO = CreateReviewDataDTO(
            id = UUID.randomUUID(),
            date = LocalDateTime.of(2025, 3, 22, 13, 24, 24),
            speedrunId = speedrunId,
            message = "message",
            moderatorId = moderatorId
        )
        whenever(speedrunRepository.findById(speedrunId)).thenReturn(Optional.of(speedrun))
        whenever(userRepository.findById(moderatorId)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            reviewDataService.createReview(createReviewDataDTO)
        }
    }

    @Test
    fun updateReviewThatNotExists() {
        val reviewId = UUID.randomUUID()
        val updateReviewDataDTO = UpdateReviewDataDTO(
            id = reviewId,
            null,  null, null, null
        )
        whenever(reviewDataRepository.findById(reviewId)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            reviewDataService.updateReview(updateReviewDataDTO)
        }
    }

    @Test
    fun deleteReviewDataThatNotExists() {
        val reviewDataId = UUID.randomUUID()
        whenever(reviewDataRepository.existsById(reviewDataId)).thenReturn(false)

        val exception = assertThrows<IllegalArgumentException> {
            reviewDataService.deleteReview(reviewDataId)
        }
        assertEquals("Review with ID $reviewDataId not found", exception.message)
    }
}