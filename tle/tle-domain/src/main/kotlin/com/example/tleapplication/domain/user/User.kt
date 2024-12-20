package com.example.tleapplication.domain.user

data class User(
    val id: Long,
    val email: String,
    var nickname: String,
    var profileImage: String,
    var role: UserRoleEnum,
    var kakaoAccessToken: String?,
    var refreshToken: String? = null
) {
    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun deleteKakaoAccessToken() {
        this.kakaoAccessToken = null
    }

    fun deleteRefreshToken() {
        this.refreshToken = null
    }
}
