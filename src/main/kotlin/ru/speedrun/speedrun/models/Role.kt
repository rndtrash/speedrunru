package ru.speedrun.speedrun.models

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
    BANNED
}
