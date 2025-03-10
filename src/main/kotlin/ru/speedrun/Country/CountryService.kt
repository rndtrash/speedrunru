package ru.speedrun.service

import org.springframework.stereotype.Service
import ru.speedrun.Country.Country
import ru.speedrun.repository.CountryRepository
import java.util.UUID

@Service
class CountryService(private val countryRepository: CountryRepository) {
    fun getAllCountries(): List<Country> {
        return countryRepository.findAll()
    }

    fun getCountryById(id: UUID): Country? {
        val existingCountry = countryRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Country with ID $id not found")
        return existingCountry
    }

    fun createCountry(country: Country): Country {
        if (country.name.isBlank()) {
            throw IllegalArgumentException("Country name cannot be empty")
        }
        if (country.name.length > 255) {
            throw IllegalArgumentException("Country name cannot exceed 255 characters")
        }
        if (country.imageLink?.length ?: 0 > 64) {
            throw IllegalArgumentException("Image link cannot exceed 64 characters")
        }
        if (countryRepository.existsByName(country.name)) {
            throw IllegalArgumentException("Country with name '${country.name}' already exists")
        }
        return countryRepository.save(country)
    }

    fun updateCountry(id: UUID, updatedCountry: Country): Country? {
        val existingCountry = countryRepository.findById(id).orElse(null)
            ?: throw IllegalArgumentException("Country with ID $id not found")
        if (updatedCountry.name.isBlank()) {
            throw IllegalArgumentException("Country name cannot be empty")
        }
        if (updatedCountry.name.length > 255) {
            throw IllegalArgumentException("Country name cannot exceed 255 characters")
        }
        if (updatedCountry.imageLink?.length ?: 0 > 64) {
            throw IllegalArgumentException("Image link cannot exceed 64 characters")
        }
        if (updatedCountry.name != existingCountry.name && countryRepository.existsByName(updatedCountry.name)) {
            throw IllegalArgumentException("Country with name '${updatedCountry.name}' already exists")
        }

        existingCountry.name = updatedCountry.name
        existingCountry.imageLink = updatedCountry.imageLink
        return countryRepository.save(existingCountry)
    }

    fun deleteCountry(id: UUID) {
        if (!countryRepository.existsById(id)) {
            throw IllegalArgumentException("Country with ID $id not found")
        }
        countryRepository.deleteById(id)
    }
}