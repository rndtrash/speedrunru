package ru.speedrun.speedrun.models

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Id
    @Column(name = "id", nullable = false)
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
)