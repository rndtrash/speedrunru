package ru.speedrun.speedrun.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/demo")
class DemoController {
    @GetMapping
    fun testAuth(): ResponseEntity<String>{
        return ResponseEntity.ok("Hello, User")
    }
}