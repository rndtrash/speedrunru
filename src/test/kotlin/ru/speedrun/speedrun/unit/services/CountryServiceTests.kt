package ru.speedrun.speedrun.unit.services

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import ru.speedrun.speedrun.dto.countries.UpdateCountryDTO
import ru.speedrun.speedrun.repositories.CountryRepository
import ru.speedrun.speedrun.services.CountryService
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CountryServiceTests {

    @Mock
    private lateinit var countryRepository: CountryRepository

    @InjectMocks
    private lateinit var countryService: CountryService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCountryByIdThatNotExist() {
        val countryId = UUID.randomUUID()
        whenever(countryRepository.findById(countryId)).thenReturn(Optional.empty())

        val result = countryService.getCountryById(countryId)

        assertNull(result)
    }

    @Test
    fun updateCountryThatNotExist() {
        val countryId = UUID.randomUUID()
        val updateCountryDTO = UpdateCountryDTO(
            id = countryId,
            name = "страна",
            flag = "картинка флага"
        )
        whenever(countryRepository.findById(countryId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException>() {
            countryService.updateCountry(updateCountryDTO)
        }
    }

    @Test
    fun deleteCountryThatNotExists() {
        val countryId = UUID.randomUUID()
        whenever(countryRepository.existsById(countryId)).thenReturn(false)

        val exception = assertThrows<IllegalArgumentException> {
            countryService.deleteCountry(countryId)
        }
        assertEquals("Country with ID $countryId not found", exception.message)
    }
}