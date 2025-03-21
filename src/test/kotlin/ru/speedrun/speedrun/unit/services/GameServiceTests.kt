package ru.speedrun.speedrun.unit.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.speedrun.speedrun.dto.games.CreateGameDTO
import ru.speedrun.speedrun.dto.games.UpdateGameDTO
import ru.speedrun.speedrun.models.Game
import ru.speedrun.speedrun.repositories.GameRepository
import ru.speedrun.speedrun.services.GameService
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GameServiceTests {

    @Mock
    private lateinit var gameRepository: GameRepository

    @InjectMocks
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getAllGamesWithMinReleaseDate() {
        val minReleaseDate = LocalDate.of(2025, 3, 22)
        val game1 = Game(
            name = "Game1",
            description = "Description1",
            releaseDate = LocalDate.of(2025, 3, 22),
            imageLink = "linkToImageOfGame1"
        )
        val game2 = Game(
            name = "Game2",
            description = "Description2",
            releaseDate = LocalDate.of(2025, 3, 23),
            imageLink = "linkToImageOfGame2"
        )
        whenever(gameRepository.findAll()).thenReturn(listOf(game1, game2))

        val result = gameService.getAllGames(minReleaseDate)

        assertEquals(1, result.size)
        assertEquals("Game2", result[0].name)
    }

    @Test
    fun getGameByIdThatNotExist() {
        val gameId = UUID.randomUUID()
        whenever(gameRepository.findById(gameId)).thenReturn(Optional.empty())

        val result = gameService.getGameById(gameId)

        assertNull(result)
    }

    @Test
    fun updateGameThatNotExist() {
        val gameId = UUID.randomUUID()
        val updateGameDTO = UpdateGameDTO(
            id = gameId,
            name = "UpdatedName",
            description = "UpdatedDescription",
            releaseDate = LocalDate.of(2025, 3, 11),
            imageLink = "UpdatedLink"
        )
        whenever(gameRepository.findById(gameId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException>() {
            gameService.updateGame(updateGameDTO)
        }
    }

    @Test
    fun deleteGameThatNotExists() {
        val gameId = UUID.randomUUID()

        whenever(gameRepository.existsById(gameId)).thenReturn(false)

        val exception = assertThrows<IllegalArgumentException> {
            gameService.deleteGame(gameId)
        }
        assertEquals("Game with ID $gameId not found", exception.message)
    }
}