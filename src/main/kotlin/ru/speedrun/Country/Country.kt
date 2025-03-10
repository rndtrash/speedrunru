package ru.speedrun.Country

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "countries")
data class Country(
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    var id: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false, length = 255, columnDefinition = "character varying(255)")
    var name: String,

    @Column(name = "image_link", length = 64, columnDefinition = "character varying(64)")
    var imageLink: String?
) {
    constructor() : this(
        id = UUID.randomUUID(),
        name = "",
        imageLink = null
    )
}