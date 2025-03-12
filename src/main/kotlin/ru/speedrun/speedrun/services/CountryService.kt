package ru.speedrun.speedrun.services

import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.CountryRequestPostDTO
import ru.speedrun.speedrun.models.Category
import ru.speedrun.speedrun.models.Country
import ru.speedrun.speedrun.repositories.CountryRepository
import java.util.UUID

@Service
class CountryService(private val countryRepository: CountryRepository) {
    fun getAllCountries(): List<Country> {
        return countryRepository.findAll()
    }

    fun getCountryById(id: UUID): Country? {
        return countryRepository.findById(id).orElse(null)
    }

    fun createCountry(request: CountryRequestPostDTO): Country {
        val country = Country(
            id = UUID.randomUUID(),
            name = request.name,
            imageLink = request.flag
        )
        return countryRepository.save(country)
    }

    fun updateCountry(id: UUID, updatedCountry: Country): Country? {
        val existingCountry = countryRepository.findById(id).get()

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