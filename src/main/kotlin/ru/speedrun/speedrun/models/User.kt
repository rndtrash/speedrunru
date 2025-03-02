package ru.speedrun.speedrun.models

import jakarta.persistence.*
import java.util.Date
import java.util.UUID

/**
 * Модель данных пользователей.
 *
 * @property id Идентификатор пользователя
 * @property name Имя пользователя
 * @property email Эл. почта
 * @property password Пароль
 * @property role Роль
 * @property regDate Дата регистрации
 * @property country Страна
 *
 * @author Ivan Abramov
 */
@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue
    var id: UUID,

    var name: String,

    var email: String,

    var password: String,

    @Enumerated(EnumType.STRING)
    var role: Role,

    var regDate: Date,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    var country: Country
)
