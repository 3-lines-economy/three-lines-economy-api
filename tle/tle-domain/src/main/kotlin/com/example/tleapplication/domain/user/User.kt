package com.example.tleapplication.domain.user

data class User(
    val id: Long,
    var nickName: String,
    var profileImage: String,
    var role: UserRoleEnum,
    var kakaoAccessToken: String?,
    var email: String? = null,
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
