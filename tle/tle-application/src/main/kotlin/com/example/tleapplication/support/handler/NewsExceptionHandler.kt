package com.example.tleapplication.support.handler

import com.example.tleapplication.support.exception.news.NewsNotFoundException
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.ErrorResponse
import com.example.tleapplication.support.response.NewsResponseCode
import com.example.tleapplication.support.response.TleApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class NewsExceptionHandler(
    private val traceIdResolver: TraceIdResolver,
) {
    @ExceptionHandler(NewsNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNewsNotFoundException(exception: NewsNotFoundException): TleApiResponse<ErrorResponse> {
        val body = ErrorResponse(exception.message)

        return TleApiResponse.of(traceIdResolver.getTraceId(), HttpStatus.NOT_FOUND, NewsResponseCode.NEWS_01, body)
    }
}
