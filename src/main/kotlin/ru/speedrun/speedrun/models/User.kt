package ru.speedrun.speedrun.models

import jakarta.persistence.*
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties.Simple
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
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
    var id: UUID,

    var name: String,

    var email: String,
    @Column(name = "password")
    var userPassword: String,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @Column(name = "reg_date")
    var regDate: Date,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = true)
    var country: Country?
) : UserDetails {
    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
       return userPassword
    }


    override fun getUsername(): String {
        return name
    }
}
