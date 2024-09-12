package com.example.tleapplication.domain.auth

import com.example.tleapplication.domain.kakao.KakaoService
import com.example.tleapplication.domain.user.User
import com.example.tleapplication.domain.user.UserRepository
import com.example.tleapplication.domain.user.UserRoleEnum
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository,
    private val tokenIssuer: TokenIssuer
) {
    fun signIn(code: String): UserToken {
        val accessToken = kakaoService.getAccessToken(code)
        val userInfo = kakaoService.getUserInfo(accessToken)

        // 회원가입 & 로그인 처리
        var user = userRepository.findUserById(userInfo!!.id)
        if (user == null) {
            user = User(
                userInfo.id,
                userInfo.kakaoAccount.profile.nickname,
                userInfo.kakaoAccount.profile.profileImageUrl,
                UserRoleEnum.ROLE_USER)
            user = userRepository.save(user)
        }

        val userToken = tokenIssuer.issueUserTokens(user.id)
        return userToken
    }
}