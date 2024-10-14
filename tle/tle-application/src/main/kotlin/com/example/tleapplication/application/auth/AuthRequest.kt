package com.example.tleapplication.application.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class AuthRequest(
    @field:NotBlank
    @Schema(description = "카카오 인가 코드")
    val code: String
) {
}