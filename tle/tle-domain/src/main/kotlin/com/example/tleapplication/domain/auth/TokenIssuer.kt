package com.example.tleapplication.domain.auth

import com.example.tleapplication.domain.user.UserRoleEnum
import org.springframework.stereotype.Component

@Component
class TokenIssuer(
    private val tokenCreator: JwtTokenCreator,
) {
    fun issueUserTokens(id: Long): UserToken {
        val role: UserRoleEnum = UserRoleEnum.ROLE_USER

        val accessToken = tokenCreator.createAccessToken(id, role)
        val refreshToken = tokenCreator.createRefreshToken()

        return UserToken.of(accessToken, refreshToken)
    }
}