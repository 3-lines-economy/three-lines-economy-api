package com.example.tleapplication.support.handler

import com.example.tleapplication.support.exception.user.UserNotFoundException
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.ErrorResponse
import com.example.tleapplication.support.response.TleApiResponse
import com.example.tleapplication.support.response.UserResponseCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler(
    private val traceIdResolver: TraceIdResolver,
) {
    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFoundException(exception: UserNotFoundException): TleApiResponse<ErrorResponse> {
        val body = ErrorResponse(exception.message)

        return TleApiResponse.of(traceIdResolver.getTraceId(), HttpStatus.NOT_FOUND, UserResponseCode.USER_01, body)
    }
}
