package ru.speedrun.speedrun.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.speedrun.speedrun.models.Speedrun
import java.util.UUID

@Repository
interface SpeedrunRepository : JpaRepository<Speedrun, UUID> {
    fun findAllByAuthorId(authorId: UUID): List<Speedrun>
    fun findByCategoryId(categoryId: UUID): List<Speedrun>
    fun findTop10ByOrderByDateDesc(): List<Speedrun>
}