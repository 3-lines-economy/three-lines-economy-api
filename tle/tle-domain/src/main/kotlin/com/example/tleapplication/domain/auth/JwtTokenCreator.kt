package com.example.tleapplication.domain.auth

import com.example.tleapplication.domain.user.UserRoleEnum
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenCreator(
    @Value("\${jwt.secret}") private val secretKeyString: String,
    @Value("\${jwt.expiration.access}") private val accessTokenExpiration: Long,
    @Value("\${jwt.expiration.refresh}") private val refreshTokenExpiration: Long,
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secretKeyString.toByteArray())

    fun createAccessToken(
        id: Long,
        role: UserRoleEnum,
    ): JwtToken {
        val now = Date()
        val expiredIn = now.time + accessTokenExpiration
        val token =
            Jwts
                .builder()
                .claim("id", id)
                .claim("role", role)
                .issuedAt(now)
                .expiration(Date(expiredIn))
                .signWith(secretKey)
                .compact()

        return JwtToken(token, accessTokenExpiration)
    }

    fun createRefreshToken(): JwtToken {
        val now = Date()
        val expiredIn = now.time + refreshTokenExpiration
        val token =
            Jwts
                .builder()
                .issuedAt(now)
                .expiration(Date(expiredIn))
                .signWith(secretKey)
                .compact()

        return JwtToken(token, refreshTokenExpiration)
    }
}