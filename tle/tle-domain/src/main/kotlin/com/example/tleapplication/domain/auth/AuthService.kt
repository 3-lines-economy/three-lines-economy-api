package com.example.tleapplication.domain.auth

import com.example.tleapplication.domain.kakao.KakaoService
import com.example.tleapplication.domain.user.User
import com.example.tleapplication.domain.user.UserRepository
import com.example.tleapplication.domain.user.UserRoleEnum
import com.example.tleapplication.support.exception.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository,
    private val tokenIssuer: TokenIssuer
) {
    fun signIn(code: String): UserToken {
        val kakaoAccessToken = kakaoService.getAccessToken(code)
        val userInfo = kakaoService.getUserInfo(kakaoAccessToken)

        // 회원가입 & 로그인 처리
        var user = userRepository.findUserById(userInfo!!.id)
        if (user == null) {
            user = User(
                userInfo.id,
                userInfo.kakaoAccount.profile.nickname,
                userInfo.kakaoAccount.profile.profileImageUrl,
                UserRoleEnum.ROLE_USER,
                kakaoAccessToken)
            user = userRepository.save(user)
        }

        val userToken = tokenIssuer.issueUserTokens(user.id)
        user.updateRefreshToken(userToken.refreshToken)
        userRepository.update(user)

        return userToken
    }

    fun signOut(id: Long) {
        val user = userRepository.findUserById(id) ?: throw UserNotFoundException()

        // 카카오 액세스 토큰 무효화 -> TODO : 프론트의 리다이렉트 필요함
        kakaoService.logout(user.kakaoAccessToken)

        user.deleteKakaoAccessToken()
        user.deleteRefreshToken()
        userRepository.update(user)
    }
}