package ru.speedrun.Game

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "games")
data class Game(
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
    var imageLink: String?
) {
    constructor() : this(
        id = UUID.randomUUID(),
        name = "",
        description = null,
        releaseDate = null,
        imageLink = null
    )
}