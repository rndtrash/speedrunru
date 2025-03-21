package ru.speedrun.speedrun.unit.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import ru.speedrun.speedrun.dto.categories.CreateCategoryDTO
import ru.speedrun.speedrun.dto.categories.UpdateCategoryDTO
import ru.speedrun.speedrun.dto.users.CreateUserDTO
import ru.speedrun.speedrun.dto.users.UpdateUserDTO
import ru.speedrun.speedrun.models.User
import ru.speedrun.speedrun.models.Country
import ru.speedrun.speedrun.repositories.UserRepository
import ru.speedrun.speedrun.repositories.CountryRepository
import ru.speedrun.speedrun.services.UserService
import ru.speedrun.speedrun.models.Role
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserServiceTests {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var countryRepository: CountryRepository

    @InjectMocks
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getUserByIdThatNotExists() {
        val userId = UUID.randomUUID()
        whenever(userRepository.findById(userId)).thenReturn(Optional.empty())

        val result = userService.getUserById(userId)

        assertNull(result)
    }

    @Test
    fun createUserWithCategoryThatNotExist() {
        val countryId = UUID.randomUUID()
        val createUserDTO = CreateUserDTO(
            countryId = countryId,
            name = "user",
            email = "email",
            password = "password",
            regDate = LocalDate.of(2025, 3, 22),
            role = "user"
        )
        whenever(countryRepository.findById(countryId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            userService.createUser(createUserDTO)
        }
    }

    @Test
    fun updateUserThatNotExists() {
        val userId = UUID.randomUUID()
        val updateUserDTO = UpdateUserDTO(
            userId, UUID.randomUUID(),"new_user", null, null, null, null
        )
        whenever(userRepository.findById(userId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            userService.updateUser(updateUserDTO)
        }
    }

    // не проходит, почему-то мы 'хаваем' несуществующий id страны, вызывая сохранение без каких-либо изменений
    @Test
    fun updateUserWithCountryThatNotExists() {
        val userId = UUID.randomUUID()
        val countryId = UUID.randomUUID()
        val updateUserDTO = UpdateUserDTO(
            id = userId,
            countryId = countryId,
            null, null, null, null, null
        )
        val existingUser = User(
            id = userId,
            country = Country(
                id = UUID.randomUUID(),
                name = "страна",
                imageLink = "картинка флага"
            ),
            name = "user",
            password = "password",
            regDate = LocalDate.of(2025, 3, 19),
            email = "email",
            role = Role.USER
        )
        whenever(userRepository.findById(userId)).thenReturn(Optional.of(existingUser))
        whenever(countryRepository.findById(countryId)).thenReturn(Optional.empty())
        whenever(userRepository.save(any())).thenAnswer { invocation ->
            invocation.getArgument<User>(0)
        }

        assertThrows<NoSuchElementException> {
            userService.updateUser(updateUserDTO)
        }
    }

    @Test
    fun deleteUserThatNotExists() {
        val userId = UUID.randomUUID()
        whenever(userRepository.existsById(userId)).thenReturn(false)

        val exception = assertThrows<IllegalArgumentException> {
            userService.deleteUser(userId)
        }
        assertEquals("User with ID $userId not found", exception.message)
    }
}