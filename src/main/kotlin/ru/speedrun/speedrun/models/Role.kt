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
}
