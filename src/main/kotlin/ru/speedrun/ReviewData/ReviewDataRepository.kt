package ru.speedrun.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.speedrun.ReviewData.ReviewData
import java.util.UUID

interface ReviewDataRepository : JpaRepository<ReviewData, UUID>