package ru.speedrun.ReviewData

import jakarta.persistence.*
import ru.speedrun.Speedrun.Speedrun
import ru.speedrun.User.User
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "review_data")
data class ReviewData(
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    var speedrun: Speedrun,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", nullable = false)
    var moderator: User,

    @Column(name = "message", nullable = false)
    var message: String,

    @Column(name = "date", nullable = false)
    var date: LocalDateTime
) {
    constructor() : this(
        id = null,
        speedrun = Speedrun(),
        moderator = User(),
        message = "",
        date = LocalDateTime.now()
    )
}