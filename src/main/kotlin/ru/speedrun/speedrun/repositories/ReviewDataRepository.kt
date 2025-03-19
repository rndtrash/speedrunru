package ru.speedrun.speedrun.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.speedrun.speedrun.models.ReviewData
import java.util.UUID

@Repository
interface ReviewDataRepository : JpaRepository<ReviewData, UUID>