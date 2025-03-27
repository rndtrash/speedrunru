package ru.speedrun.speedrun.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.categories.UpdateCategoryDTO
import ru.speedrun.speedrun.models.User
import ru.speedrun.speedrun.dto.users.CreateUserDTO
import ru.speedrun.speedrun.dto.users.UpdateUserDTO
import ru.speedrun.speedrun.dto.users.UpdateUserProfileDTO
import ru.speedrun.speedrun.dto.users.UserProfileDTO
import ru.speedrun.speedrun.models.Category
import ru.speedrun.speedrun.services.UserService
import java.util.UUID

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getAllUsers(): List<User>? = userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): User? = userService.getUserById(id)

    @GetMapping("/{username}/profile")
    fun getUserProfile(@PathVariable username: String): UserProfileDTO = userService.getUserProfile(username)

    @PostMapping
    fun createUser(@RequestBody request: CreateUserDTO): User = userService.createUser(request)

    @PatchMapping
    fun updateUser(@RequestBody request: UpdateUserDTO): User = userService.updateUser(request)

    @PatchMapping("/{username}/profile")
    fun updateUserProfile(@PathVariable username: String, @RequestBody dto: UpdateUserProfileDTO): User =
        userService.updateUserProfile(username, dto)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID) {
        userService.deleteUser(id)
    }
}