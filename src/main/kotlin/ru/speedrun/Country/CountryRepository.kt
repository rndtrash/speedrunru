package ru.speedrun.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.speedrun.Country.Country
import java.util.UUID

interface CountryRepository : JpaRepository<Country, UUID> {
    fun existsByName(name: String): Boolean
}