package com.example.tleapplication.support.handler

import com.example.tleapplication.support.exception.auth.ClaimNotFoundException
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.AuthResponseCode
import com.example.tleapplication.support.response.ErrorResponse
import com.example.tleapplication.support.response.TleApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthExceptionHandler(
    private val traceIdResolver: TraceIdResolver,
) {
    @ExceptionHandler(ClaimNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleClaimNotFoundException(exception: ClaimNotFoundException): TleApiResponse<ErrorResponse> {
        val body = ErrorResponse(exception.message)

        return TleApiResponse.of(traceIdResolver.getTraceId(), HttpStatus.NOT_FOUND, AuthResponseCode.AUTH_01, body)
    }
}
