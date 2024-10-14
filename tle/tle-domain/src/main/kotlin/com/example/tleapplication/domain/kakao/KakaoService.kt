package com.example.tleapplication.domain.kakao

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class KakaoService(
    @Value("\${kakao.client_id}") private val clientId: String,
    @Value("\${kakao.redirect_uri}") private val redirectUri: String,
) {
    private val KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com"
    private val KAUTH_USER_URL_HOST = "https://kapi.kakao.com"

    fun getAccessToken(code: String): String? {
        val kakaoToken = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("https")
                    .path("/oauth/token")
                    .queryParam("grant_type", "authorization_code")
                    .queryParam("client_id", clientId)
                    .queryParam("redirect_uri", redirectUri)
                    .queryParam("code", code)
                    .build(true)
            }
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .retrieve()
            .bodyToMono(KakaoToken::class.java)
            .block()

        return kakaoToken?.accessToken
    }

    fun getUserInfo(accessToken: String?): KakaoUserInfo? {
        val userInfo = WebClient.create(KAUTH_USER_URL_HOST).get()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("https")
                    .path("/v2/user/me")
                    .build(true)
            }
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .retrieve()
            .bodyToMono(KakaoUserInfo::class.java)
            .block()

        return userInfo
    }

    fun logout(accessToken: String?) {
        val userInfo = WebClient.create(KAUTH_USER_URL_HOST).post()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("https")
                    .path("/v1/user/logout")
                    .build(true)
            }
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .retrieve()
            .bodyToMono(Void::class.java)
            .block()
    }
}