package ru.speedrun.services

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.speedrun.speedrun.models.User
import ru.speedrun.speedrun.repositories.UserRepository

/**
 * Сервис для загрузки информации о пользователях из базы данных.
 *
 * Реализует интерфейс `UserDetailsService` для интеграции со Spring Security.
 */
@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    /**
     * Загружает пользователя по email.
     *
     * @param email Электронная почта пользователя.
     * @return UserDetails объект для аутентификации.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findByEmail(email).get()
        val securityUser = org.springframework.security.core.userdetails.User(
            user.name,
            user.password,
            user.role.getAuthorities()
        )
        return securityUser
    }
}