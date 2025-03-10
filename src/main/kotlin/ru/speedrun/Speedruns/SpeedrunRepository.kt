package ru.speedrun.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.speedrun.Speedrun.Speedrun
import java.util.UUID

interface SpeedrunRepository : JpaRepository<Speedrun, UUID> {
    fun existsByAuthorIdAndCategoryIdAndTime(authorId: UUID, categoryId: UUID, time: Long): Boolean
}