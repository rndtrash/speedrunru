package ru.speedrun.speedrun.services

import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.UserRequestDTO
import ru.speedrun.speedrun.models.User
import ru.speedrun.speedrun.repositories.CountryRepository
import ru.speedrun.speedrun.repositories.UserRepository
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val countryRepository: CountryRepository
) {
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUserById(id: UUID): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun createUser(request: UserRequestDTO): User {
        val country = countryRepository.findById(request.countryId).get()
        val user = User(
            country = country,
            name = request.name,
            email = request.email,
            password = request.password,
            regDate = request.regDate,
            role = request.role
        )
        return userRepository.save(user)
    }

    fun updateUser(id: UUID, request: UserRequestDTO): User {
        val existingUser = userRepository.findById(id).get()
        val country = countryRepository.findById(request.countryId).get()

        existingUser.country = country
        existingUser.name = request.name
        existingUser.email = request.email
        existingUser.password = request.password
        existingUser.regDate = request.regDate
        existingUser.role = request.role
        return userRepository.save(existingUser)
    }

    fun deleteUser(id: UUID) {
        if (!userRepository.existsById(id)) {
            throw IllegalArgumentException("User with ID $id not found")
        }
        userRepository.deleteById(id)
    }
}