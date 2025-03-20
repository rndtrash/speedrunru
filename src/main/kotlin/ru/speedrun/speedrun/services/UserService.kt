package ru.speedrun.speedrun.services

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.users.CreateUserDTO
import ru.speedrun.speedrun.dto.users.UpdateUserDTO
import ru.speedrun.speedrun.dto.users.UpdateUserProfileDTO
import ru.speedrun.speedrun.dto.users.UserProfileDTO
import ru.speedrun.speedrun.models.Country
import ru.speedrun.speedrun.models.Role
import ru.speedrun.speedrun.models.Status
import ru.speedrun.speedrun.models.User
import ru.speedrun.speedrun.repositories.CountryRepository
import ru.speedrun.speedrun.repositories.SpeedrunRepository
import ru.speedrun.speedrun.repositories.UserRepository
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val countryRepository: CountryRepository,
    private val speedrunRepository: SpeedrunRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUserById(id: UUID): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun getUserProfile(username: String): UserProfileDTO {
        val user = userRepository.findByName(username).get()
        val speedruns = speedrunRepository.findAllByAuthorId(user.id)
        return UserProfileDTO(
            username = username,
            avatar = user.imageLink,
            country = user.country,
            acceptedRuns = speedruns.filter { it.status == Status.VERIFIED },
            inReviewRuns = speedruns.filter { it.status == Status.PROCESSING },
            declinedRuns = null
        )
    }

    fun createUser(request: CreateUserDTO): User {
        val country = countryRepository.findById(request.countryId).get()
        val user = User(
            country = country,
            name = request.name,
            email = request.email,
            userPassword = request.password,
            regDate = request.regDate,
            imageLink = request.imageLink,
            role = Role.valueOf(request.role.uppercase())
        )
        return userRepository.save(user)
    }

    fun updateUser(request: UpdateUserDTO): User {
        val user = userRepository.findById(request.id).get()
        request.name?.let { user.name = it }
        request.email?.let { user.email = it }
        request.password?.let { user.userPassword = it }
        request.countryId?.let { countryId ->
            if (countryRepository.existsById(countryId)) {
                user.country = countryRepository.findById(countryId).get()
            }
        }
        request.regDate?.let { user.regDate = it }
        request.role?.let { user.role = Role.valueOf(it.uppercase()) }
        return userRepository.save(user)
    }

    fun updateUserProfile(username: String, dto: UpdateUserProfileDTO): User {
        val user = userRepository.findByName(username).get()
        dto.password?.let { user.userPassword = passwordEncoder.encode(it) }
        dto.avatar?.let { user.imageLink = it }
        dto.countryId?.let { countryId ->
            if (countryRepository.existsById(countryId)) {
                user.country = countryRepository.findById(countryId).get()
            }
        }
        return userRepository.save(user)
    }

    fun deleteUser(id: UUID) {
        if (!userRepository.existsById(id)) {
            throw IllegalArgumentException("User with ID $id not found")
        }
        userRepository.deleteById(id)
    }
}