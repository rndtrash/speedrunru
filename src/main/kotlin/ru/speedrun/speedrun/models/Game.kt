package ru.speedrun.speedrun.models

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "games")
class Game(
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false, length = 255)
    var name: String,

    @Column(name = "description")
    var description: String?,

    @Column(name = "release_date")
    var releaseDate: LocalDate?,

    @Column(name = "image_link", length = 64)
    var imageLink: String?,
)
