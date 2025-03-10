package ru.speedrun.service

import org.springframework.stereotype.Service
import ru.speedrun.User.User
import ru.speedrun.repository.CountryRepository
import ru.speedrun.repository.UserRepository
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
        val existingUser = userRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("User with ID $id not found")
        return existingUser
    }

    fun createUser(user: User): User {
        if (user.name.isBlank()) throw IllegalArgumentException("User name cannot be empty")
        if (user.name.length > 255) throw IllegalArgumentException("User name cannot exceed 255 characters")
        if (user.email.isBlank()) throw IllegalArgumentException("Email cannot be empty")
        if (user.email.length > 255) throw IllegalArgumentException("Email cannot exceed 255 characters")
        if (!isValidEmail(user.email)) throw IllegalArgumentException("Invalid email format")
        if (user.password.isBlank()) throw IllegalArgumentException("Password cannot be empty")
        if (user.password.length > 255) throw IllegalArgumentException("Password cannot exceed 255 characters")
        if (user.role.isBlank()) throw IllegalArgumentException("Role cannot be empty")
        if (user.role.length > 100) throw IllegalArgumentException("Role cannot exceed 100 characters")
        if (userRepository.existsByEmail(user.email)) throw IllegalArgumentException("Email '${user.email}' is already taken")
        if (!countryRepository.existsById(user.country.id)) throw IllegalArgumentException("Country with ID ${user.country.id} not found")

        return userRepository.save(user)
    }

    fun updateUser(id: UUID, updatedUser: User): User {
        val existingUser = userRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("User with ID $id not found")
        if (updatedUser.name.isBlank()) throw IllegalArgumentException("User name cannot be empty")
        if (updatedUser.name.length > 255) throw IllegalArgumentException("User name cannot exceed 255 characters")
        if (updatedUser.email.isBlank()) throw IllegalArgumentException("Email cannot be empty")
        if (updatedUser.email.length > 255) throw IllegalArgumentException("Email cannot exceed 255 characters")
        if (!isValidEmail(updatedUser.email)) throw IllegalArgumentException("Invalid email format")
        if (updatedUser.password.isBlank()) throw IllegalArgumentException("Password cannot be empty")
        if (updatedUser.password.length > 255) throw IllegalArgumentException("Password cannot exceed 255 characters")
        if (updatedUser.role.isBlank()) throw IllegalArgumentException("Role cannot be empty")
        if (updatedUser.role.length > 100) throw IllegalArgumentException("Role cannot exceed 100 characters")
        if (updatedUser.email != existingUser.email && userRepository.existsByEmail(updatedUser.email)) {
            throw IllegalArgumentException("Email '${updatedUser.email}' is already taken")
        }
        if (!countryRepository.existsById(updatedUser.country.id)) throw IllegalArgumentException("Country with ID ${updatedUser.country.id} not found")

        existingUser.country = updatedUser.country
        existingUser.name = updatedUser.name
        existingUser.email = updatedUser.email
        existingUser.password = updatedUser.password
        existingUser.regDate = updatedUser.regDate
        existingUser.role = updatedUser.role
        return userRepository.save(existingUser)
    }

    fun deleteUser(id: UUID) {
        if (!userRepository.existsById(id)) {
            throw IllegalArgumentException("User with ID $id not found")
        }
        userRepository.deleteById(id)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }
}