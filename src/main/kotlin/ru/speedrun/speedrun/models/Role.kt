package ru.speedrun.speedrun.models

import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 *  Роли для пользователя.
 *
 *  @author Ivan Abramov
 */
enum class Role {
    /**
     * Администратор
     */
    ADMIN,

    /**
     * Модератор
     */
    MODERATOR,

    /**
     * Пользователь
     */
    USER,

    /**
     * Заблокирован
     */
    BANNED;

    fun getAuthorities(): List<SimpleGrantedAuthority> {
        return when (this) {
            ADMIN -> listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
            MODERATOR -> listOf(SimpleGrantedAuthority("ROLE_MODERATOR"))
            USER -> listOf(SimpleGrantedAuthority("ROLE_USER"))
            BANNED -> listOf()
        }
    }
}
