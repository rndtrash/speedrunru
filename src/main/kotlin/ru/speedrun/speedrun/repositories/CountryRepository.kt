package ru.speedrun.speedrun.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.speedrun.speedrun.models.Country
import java.util.UUID

/**
 * Интерфейс для работы с моделями данных стран.
 *
 * @author Ivan Abramov
 */
interface CountryRepository: JpaRepository<Country, UUID>
