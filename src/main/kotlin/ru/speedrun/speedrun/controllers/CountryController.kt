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
//    @GetMapping
//    fun getAllCountries(): List<Country>? = countryService.getAllCountries()

    @GetMapping
    fun getAllCountries(): ResponseEntity<Map<String, Any>> {
        return try {
            val countries = countryService.getAllCountries().map { it.toRequestDTO() }
            ResponseEntity.ok(mapOf("countries" to countries))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Error getting list of countries"))
        }
    }

    @GetMapping("/{id}")
    fun getCountryById(@PathVariable id: UUID): Country? = countryService.getCountryById(id)

    @PostMapping
    fun createCountry(@RequestBody request: CreateCountryDTO): Country = countryService.createCountry(request)

    @PatchMapping
    fun updateCountry(@RequestBody request: UpdateCountryDTO): Country = countryService.updateCountry(request)

    @DeleteMapping("/{id}")
    fun deleteCountry(@PathVariable id: UUID) {
        countryService.deleteCountry(id)
    }
}