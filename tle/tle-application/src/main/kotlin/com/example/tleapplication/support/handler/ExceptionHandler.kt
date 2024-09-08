package com.example.tleapplication.support.handler

import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.CommonResponseCode
import com.example.tleapplication.support.response.ErrorResponse
import com.example.tleapplication.support.response.TleApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler(
    private val traceIdResolver: TraceIdResolver,
) {
    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): TleApiResponse<ErrorResponse> {
        val body = ErrorResponse(exception.message)

        return TleApiResponse.of(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            code = CommonResponseCode.COMMON_01,
            body = body
        )
    }
}