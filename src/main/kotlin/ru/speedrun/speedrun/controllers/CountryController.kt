package ru.speedrun.speedrun.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.speedrun.dto.toResponseDTO
import ru.speedrun.speedrun.models.Country
import ru.speedrun.speedrun.services.CountryService
import java.util.UUID

@RestController
@RequestMapping("/api/country")
class CountryController(private val countryService: CountryService) {
    @GetMapping
    fun getAllCountries(): ResponseEntity<out Map<String, Any>> {
        val countries = countryService.getAllCountries()
        return if (countries != null) {
            ResponseEntity.ok(mapOf("countries" to countries.map { it.toResponseDTO() }))
        } else {
            ResponseEntity.status(500).body(mapOf("message" to "?????? ????????? ?????? ?????"))
        }
    }

    @GetMapping("/{id}")
    fun getCountryById(@PathVariable id: UUID): ResponseEntity<Country> {
        return ResponseEntity.ofNullable(countryService.getCountryById(id))
    }

    @PostMapping
    fun createCountry(@RequestBody country: Country): ResponseEntity<Country> {
        val createdCountry = countryService.createCountry(country)
        return ResponseEntity.status(201).body(createdCountry)
    }

    @PutMapping("/{id}")
    fun updateCountry(@PathVariable id: UUID, @RequestBody updatedCountry: Country): ResponseEntity<Country> {
        val country = countryService.updateCountry(id, updatedCountry)
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