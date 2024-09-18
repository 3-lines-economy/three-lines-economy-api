package com.example.tleapplication.domain.auth

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class TokenAuthService(
    private val claimExtractor: ClaimExtractor,
) {
    fun authenticate(token: String?) {
        val claims = claimExtractor.extractAllClaims(token)

        if (claims != null) {
            val role = claims.get("role", String::class.java)
            val authority = SimpleGrantedAuthority(role)

            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken((claims["id"] as Double).toLong(), "", listOf(authority))
        }
    }
}