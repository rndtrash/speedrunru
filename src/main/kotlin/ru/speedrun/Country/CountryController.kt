package ru.speedrun.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.speedrun.Country.Country
import ru.speedrun.service.CountryService
import java.util.UUID

@RestController
@RequestMapping("/api/countries")
class CountryController(private val countryService: CountryService) {
    @GetMapping
    fun getAllCountries(): ResponseEntity<List<Country>> {
        val countries = countryService.getAllCountries()
        return ResponseEntity.ok(countries)
    }

    @GetMapping("/{id}")
    fun getCountryById(@PathVariable id: UUID): ResponseEntity<Country> {
        val country = countryService.getCountryById(id)
        return if (country != null) {
            ResponseEntity.ok(country)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createCountry(@RequestBody country: Country): ResponseEntity<Country> {
        val createdCountry = countryService.createCountry(country)
        return ResponseEntity.status(201).body(createdCountry)
    }

    @PutMapping("/{id}")
    fun updateCountry(@PathVariable id: UUID, @RequestBody updatedCountry: Country): ResponseEntity<Country> {
        updatedCountry.id = id
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