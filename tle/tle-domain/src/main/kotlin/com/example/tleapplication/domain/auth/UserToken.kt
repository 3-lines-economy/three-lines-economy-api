package com.example.tleapplication.domain.auth

data class UserToken(
    val accessToken: String,
    val accessTokenExpiredIn: Long,
    val refreshToken: String,
    val refreshTokenExpiredIn: Long,
) {
    companion object {
        fun of(accessToken: JwtToken, refreshToken: JwtToken): UserToken {
            return UserToken(
                accessToken = accessToken.value,
                accessTokenExpiredIn = accessToken.expiredIn,
                refreshToken = refreshToken.value,
                refreshTokenExpiredIn = refreshToken.expiredIn
            )
        }
    }
}