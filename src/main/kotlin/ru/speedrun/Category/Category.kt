package ru.speedrun.category

import ru.speedrun.Game.Game
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    var game: Game,

    @Column(name = "name", nullable = false, length = 200)
    var name: String,

    @Column(name = "description")
    var description: String?

) {
    constructor() : this(
        id = UUID.randomUUID(),
        game = Game(),
        name = "",
        description = null
    )
}