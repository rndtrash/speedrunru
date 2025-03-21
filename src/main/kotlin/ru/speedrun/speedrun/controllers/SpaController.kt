package ru.speedrun.speedrun.controllers

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class SpaController : ErrorController {
    @RequestMapping("/error")
    fun error(request: HttpServletRequest, response: HttpServletResponse): Any {
        // Хак: если ручка не найдена, то передаём управление фронтенду
        if (request.method.equals(HttpMethod.GET.name(), ignoreCase = true)) {
            response.status = HttpStatus.OK.value()
            return "forward:/index.html"
        } else {
            return ResponseEntity.notFound().build<Any>()
        }
    }

    fun getErrorPath(): String {
        return "/error"
    }
}
