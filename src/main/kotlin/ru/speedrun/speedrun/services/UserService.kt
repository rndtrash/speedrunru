package ru.speedrun.speedrun.services

import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.users.CreateUserDTO
import ru.speedrun.speedrun.dto.users.UpdateUserDTO
import ru.speedrun.speedrun.models.Role
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

    fun createUser(request: CreateUserDTO): User {
        val country = countryRepository.findById(request.countryId).get()
        val user = User(
            country = country,
            name = request.name,
            email = request.email,
            userPassword = request.password,
            regDate = request.regDate,
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

    fun deleteUser(id: UUID) {
        if (!userRepository.existsById(id)) {
            throw IllegalArgumentException("User with ID $id not found")
        }
        userRepository.deleteById(id)
    }
}