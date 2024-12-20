package com.example.tleapplication.domain.auth

import com.example.tleapplication.domain.user.User

data class SignInInfo(
    val userToken: UserToken,
    val nickname: String,
    val email: String,
    val profileImage: String
) {
    companion object {
        fun of(userToken: UserToken, user: User): SignInInfo {
            return SignInInfo(
                userToken = userToken,
                nickname = user.nickname,
                email = user.email,
                profileImage = user.profileImage
            )
        }
    }
}