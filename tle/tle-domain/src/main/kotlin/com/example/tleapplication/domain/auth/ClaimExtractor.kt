package com.example.tleapplication.domain.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class ClaimExtractor(
    @Value("\${jwt.secret}") private val secretKeyString: String,
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secretKeyString.toByteArray())

    fun extractAllClaims(token: String?): Claims? {
        if (token == null) return null

        val replacedToken: String

        if (token.contains("Bearer ")) {
            replacedToken = token.replace("Bearer ", "")
        } else {
            throw DecodingException("")
        }

        return Jwts
            .parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(replacedToken)
            .payload
    }
}