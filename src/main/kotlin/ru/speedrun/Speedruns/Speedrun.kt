package ru.speedrun.Speedrun

import ru.speedrun.category.Category
import jakarta.persistence.*
import ru.speedrun.User.User
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "speedruns")
data class Speedrun(
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

    @Column(name = "status", nullable = false, length = 100)
    var status: String
) {
    constructor() : this(
        id = UUID.randomUUID(),
        category = Category(),
        author = User(),
        date = LocalDateTime.now(),
        link = null,
        time = 0L,
        status = ""
    )
}