package ru.speedrun.speedrun.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

/**
 * Модель данных стран.
 *
 * @property id Идентификатор страны
 * @property name Наименование страны
 * @property imageLink Ссылка на изображение
 *
 * @author Ivan Abramov
 */
@Entity
@Table(name = "countries")
class Country(
    @Id
    var id: UUID = UUID.randomUUID(),

    var name: String,

    var imageLink: String?
)
