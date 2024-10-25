package com.example.tleapplication.support.handler

import com.example.tleapplication.support.exception.scrap.ScrapAlreadyExistsException
import com.example.tleapplication.support.exception.scrap.ScrapNotFoundException
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.ErrorResponse
import com.example.tleapplication.support.response.ScrapResponseCode
import com.example.tleapplication.support.response.TleApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ScrapExceptionHandler(
    private val traceIdResolver: TraceIdResolver,
) {
    @ExceptionHandler(ScrapAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleScrapAlreadyExistsException(exception: ScrapAlreadyExistsException): TleApiResponse<ErrorResponse> {
        val body = ErrorResponse(exception.message)

        return TleApiResponse.of(traceIdResolver.getTraceId(), HttpStatus.BAD_REQUEST, ScrapResponseCode.SCRAP_01, body)
    }

    @ExceptionHandler(ScrapNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleScrapNotFoundException(exception: ScrapNotFoundException): TleApiResponse<ErrorResponse> {
        val body = ErrorResponse(exception.message)

        return TleApiResponse.of(traceIdResolver.getTraceId(), HttpStatus.NOT_FOUND, ScrapResponseCode.SCRAP_02, body)
    }
}
