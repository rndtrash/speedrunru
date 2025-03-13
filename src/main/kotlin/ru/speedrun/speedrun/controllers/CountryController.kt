package ru.speedrun.speedrun.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.countries.CreateCountryDTO
import ru.speedrun.speedrun.dto.countries.UpdateCountryDTO
import ru.speedrun.speedrun.dto.countries.toRequestDTO
import ru.speedrun.speedrun.models.Country
import ru.speedrun.speedrun.services.CountryService
import java.util.UUID

@RestController
@RequestMapping("/api/country")
class CountryController(private val countryService: CountryService) {
    @GetMapping
    fun getAllCountries(): ResponseEntity<out Map<String, Any>> {
        val countries = countryService.getAllCountries()
        return ResponseEntity.ok(mapOf("countries" to countries.map { it.toRequestDTO() }))
    }

    @GetMapping("/{id}")
    fun getCountryById(@PathVariable id: UUID): ResponseEntity<Country> {
        return ResponseEntity.ofNullable(countryService.getCountryById(id))
    }

    @PostMapping
    fun createCountry(@RequestBody request: CreateCountryDTO): ResponseEntity<Country> {
        val createdCountry = countryService.createCountry(request)
        return ResponseEntity.status(201).body(createdCountry)
    }

    @PatchMapping("/{id}")
    fun updateCountry(@RequestBody request: UpdateCountryDTO): ResponseEntity<Country> {
        val country = countryService.updateCountry(request)
        return if (country != null) {
            ResponseEntity.ok(country)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCountry(@PathVariable id: UUID): ResponseEntity<Void> {
        countryService.deleteCountry(id)
        return ResponseEntity.noContent().build()
    }
}