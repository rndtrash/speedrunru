package ru.speedrun.speedrun.repositories

import ru.speedrun.speedrun.models.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategoryRepository : JpaRepository<Category, UUID>