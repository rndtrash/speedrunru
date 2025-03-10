package ru.speedrun.repository

import ru.speedrun.category.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategoryRepository : JpaRepository<Category, UUID> {
    fun existsByNameAndGameId(name: String, gameId: UUID): Boolean
}