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
        val kakaoAccessToken = kakaoService.getKakaoAccessToken(code)
        val userInfo = kakaoService.getUserInfo(kakaoAccessToken)

        var kakaoUser = userRepository.findUserById(userInfo!!.id)
        if (kakaoUser == null) {
            kakaoUser = User(
                userInfo.id,
                userInfo.kakaoAccount.profile.nickname,
                userInfo.kakaoAccount.profile.profileImageUrl,
                UserRoleEnum.ROLE_USER)
            kakaoUser = userRepository.save(kakaoUser)
        }

        val userToken = tokenIssuer.issueUserTokens(kakaoUser.id)
        return userToken
    }
}