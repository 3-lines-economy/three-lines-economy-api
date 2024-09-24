package com.example.tleapplication.domain.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserInfo(
    // 회원 번호
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount
) {
    data class KakaoAccount(
        @JsonProperty("profile")
        val profile: Profile
    ) {
        data class Profile(
            // 닉네임
            @JsonProperty("nickname")
            val nickname: String,

            // 프로필 사진 URL
            @JsonProperty("profile_image_url")
            val profileImageUrl: String
        )
    }
}