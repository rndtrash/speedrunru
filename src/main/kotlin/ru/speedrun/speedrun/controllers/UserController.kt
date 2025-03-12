package ru.speedrun.speedrun.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.models.User
import ru.speedrun.speedrun.dto.UserRequestDTO
import ru.speedrun.speedrun.services.UserService
import java.util.UUID

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<User> {
        return ResponseEntity.ofNullable(userService.getUserById(id))
    }

    @PostMapping
    fun createUser(@RequestBody request: UserRequestDTO): ResponseEntity<User> {
        val createdUser = userService.createUser(request)
        return ResponseEntity.status(201).body(createdUser)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody request: UserRequestDTO): ResponseEntity<User> {
        val updatedUser = userService.updateUser(id, request)
        return ResponseEntity.ok(updatedUser)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}