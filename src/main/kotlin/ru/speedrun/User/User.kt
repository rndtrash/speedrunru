package ru.speedrun.User

import jakarta.persistence.*
import ru.speedrun.Country.Country
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    var id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    var country: Country,

    @Column(name = "name", nullable = false, length = 255)
    var name: String,

    @Column(name = "email", nullable = false, length = 255)
    var email: String,

    @Column(name = "password", nullable = false, length = 255)
    var password: String,

    @Column(name = "reg_date", nullable = false)
    var regDate: LocalDate,

    @Column(name = "role", nullable = false, length = 100)
    var role: String
) {
    constructor() : this(
        id = UUID.randomUUID(),
        country = Country(),
        name = "",
        email = "",
        password = "",
        regDate = LocalDate.now(),
        role = ""
    )
}