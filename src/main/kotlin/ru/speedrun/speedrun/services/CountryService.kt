package ru.speedrun.speedrun.services

import org.springframework.stereotype.Service
import ru.speedrun.speedrun.dto.countries.CreateCountryDTO
import ru.speedrun.speedrun.dto.countries.UpdateCountryDTO
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

    fun createCountry(request: CreateCountryDTO): Country {
        val country = Country(
            id = UUID.randomUUID(),
            name = request.name,
            imageLink = request.flag
        )
        return countryRepository.save(country)
    }

    fun updateCountry(request: UpdateCountryDTO): Country {
        val country = countryRepository.findById(request.id).get()
        request.name?.let {country.name = it}
        request.flag?.let {country.imageLink = it}
        return countryRepository.save(country)
    }

    fun deleteCountry(id: UUID) {
        if (!countryRepository.existsById(id)) {
            throw IllegalArgumentException("Country with ID $id not found")
        }
        countryRepository.deleteById(id)
    }
}