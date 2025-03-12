package ru.speedrun.speedrun.models

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

enum class Status { VERIFIED, REJECTED, PROCESSING }

@Entity
@Table(name = "speedruns")
class Speedrun(
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    var author: User,

    @Column(name = "date", nullable = false)
    var date: LocalDateTime,

    @Column(name = "link", length = 255)
    var link: String?,

    @Column(name = "time", nullable = false)
    var time: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 100)
    var status: Status
)