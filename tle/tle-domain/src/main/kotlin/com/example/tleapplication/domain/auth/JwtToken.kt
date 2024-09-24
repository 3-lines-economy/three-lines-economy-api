package com.example.tleapplication.domain.auth

data class JwtToken(
    val value: String,
    val expiredIn: Long,
)