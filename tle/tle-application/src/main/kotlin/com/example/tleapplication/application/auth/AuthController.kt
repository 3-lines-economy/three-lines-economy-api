package com.example.tleapplication.application.auth

import com.example.tleapplication.domain.auth.AuthService
import com.example.tleapplication.domain.auth.UserToken
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.TleApiResponse
import com.example.tleapplication.support.security.Auth
import com.example.tleapplication.support.security.AuthInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "인증 API", description = "인증 API")
class AuthController(
    private val traceIdResolver: TraceIdResolver,
    private val authService: AuthService
) {
    companion object {
        const val SUCCESS = "OK"
    }

    @Operation(
        summary = "카카오 로그인",
        description = "카카오 로그인 API",
        responses = [
            ApiResponse(responseCode = "200", description = "로그인 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@Valid @RequestBody authRequest: AuthRequest): TleApiResponse<UserToken> {
        val userToken = authService.signIn(authRequest.code)
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            userToken
        )
    }

    @Operation(
        summary = "카카오 로그아웃",
        description = "카카오 로그아웃 API",
        responses = [
            ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @PostMapping("/sign-out")
    @ResponseStatus(HttpStatus.OK)
    fun signOut(@Auth authInfo: AuthInfo): TleApiResponse<String> {
        authService.signOut(authInfo.userId)
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            SUCCESS
        )
    }

    // TODO : 토큰 재발급
}