package com.example.tleapplication.domain.user

data class User(
    val id: Long,
    var nickName: String,
    var profileImage: String,
    var role: UserRoleEnum,
    var email: String? = null,
    var refreshToken: String? = null,
) {
}
