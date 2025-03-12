package ru.speedrun.speedrun.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "countries")
class Country(
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false, length = 255)
    var name: String,

    @Column(name = "image_link", length = 64)
    var imageLink: String?
)