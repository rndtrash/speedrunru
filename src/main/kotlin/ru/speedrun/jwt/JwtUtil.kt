package ru.speedrun.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import ru.speedrun.speedrun.models.User

import java.util.*

@Component
class JwtUtil {
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.email)
            .claim("role", user.role.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60)) // срок действия 1 час
            .signWith(secretKey)
            .compact()
    }

    fun extractEmail(token: String): String? {
        return try {
            getClaims(token).subject
        }catch (e: Exception) {
            null
        }
    }

    fun validateToken(token: String, userDetails: org.springframework.security.core.userdetails.UserDetails): Boolean {
        val email = extractEmail(token)
        return (email == userDetails.username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getClaims(token).expiration
        return expiration.before(Date())
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

}