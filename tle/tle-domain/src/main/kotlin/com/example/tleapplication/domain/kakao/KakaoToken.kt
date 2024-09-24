package com.example.tleapplication.domain.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoToken(
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Integer,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Integer,
    @JsonProperty("scope")
    val scope: String,
) {
}