package com.example.tleapplication.domain.auth

import com.example.tleapplication.domain.kakao.KakaoService
import com.example.tleapplication.domain.user.User
import com.example.tleapplication.domain.user.UserRoleEnum
import com.example.tleapplication.domain.user.UserService
import com.example.tleapplication.support.exception.user.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val kakaoService: KakaoService,
    private val userService: UserService,
    private val tokenIssuer: TokenIssuer
) {
    @Transactional
    fun signIn(code: String): UserToken {
        val kakaoAccessToken = kakaoService.getAccessToken(code)
        val userInfo = kakaoService.getUserInfo(kakaoAccessToken)

        // 회원가입 & 로그인 처리
        var user = userService.findUserById(userInfo!!.id)
        if (user == null) {
            user = User(
                userInfo.id,
                userInfo.kakaoAccount.profile.nickname,
                userInfo.kakaoAccount.profile.profileImageUrl,
                UserRoleEnum.ROLE_USER,
                kakaoAccessToken)
            user = userService.saveUser(user)
        }

        val userToken = tokenIssuer.issueUserTokens(user.id)
        user.updateRefreshToken(userToken.refreshToken)
        userService.saveUser(user)

        return userToken
    }

    @Transactional
    fun signOut(id: Long) {
        val user = userService.findUserById(id) ?: throw UserNotFoundException()

        // 카카오 액세스 토큰 무효화 -> TODO : 프론트의 리다이렉트 필요함
        kakaoService.logout(user.kakaoAccessToken)

        user.deleteKakaoAccessToken()
        user.deleteRefreshToken()
        userService.saveUser(user)
    }
}