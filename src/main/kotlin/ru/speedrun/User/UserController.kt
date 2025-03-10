package ru.speedrun.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.User.User
import ru.speedrun.request.UserRequestDTO
import ru.speedrun.service.CountryService
import ru.speedrun.service.UserService
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val countryService: CountryService
) {
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<User> {
        val user = userService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createUser(@RequestBody request: UserRequestDTO): ResponseEntity<User> {
        val country = countryService.getCountryById(request.countryId)
            ?: throw IllegalArgumentException("Country with ID ${request.countryId} not found")
        val user = User(
            country = country,
            name = request.name,
            email = request.email,
            password = request.password,
            regDate = request.regDate,
            role = request.role,
        )
        val createdUser = userService.createUser(user)
        return ResponseEntity.status(201).body(createdUser)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody request: UserRequestDTO): ResponseEntity<User> {
        val country = countryService.getCountryById(request.countryId)
            ?: throw IllegalArgumentException("Country with ID ${request.countryId} not found")
        val updatedUser = User(
            country = country,
            name = request.name,
            email = request.email,
            password = request.password,
            regDate = request.regDate,
            role = request.role
        )
        val user = userService.updateUser(id, updatedUser)
        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}