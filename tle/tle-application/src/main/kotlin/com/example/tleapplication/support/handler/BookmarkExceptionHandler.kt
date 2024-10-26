package com.example.tleapplication.support.handler

import com.example.tleapplication.support.exception.bookmark.BookmarkAlreadyExistsException
import com.example.tleapplication.support.exception.bookmark.BookmarkNotFoundException
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.BookmarkResponseCode
import com.example.tleapplication.support.response.ErrorResponse
import com.example.tleapplication.support.response.TleApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BookmarkExceptionHandler(
    private val traceIdResolver: TraceIdResolver,
) {
    @ExceptionHandler(BookmarkAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBookmarkAlreadyExistsException(exception: BookmarkAlreadyExistsException): TleApiResponse<ErrorResponse> {
        val body = ErrorResponse(exception.message)

        return TleApiResponse.of(traceIdResolver.getTraceId(), HttpStatus.BAD_REQUEST, BookmarkResponseCode.BOOKMARK_01, body)
    }

    @ExceptionHandler(BookmarkNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBookmarkNotFoundException(exception: BookmarkNotFoundException): TleApiResponse<ErrorResponse> {
        val body = ErrorResponse(exception.message)

        return TleApiResponse.of(traceIdResolver.getTraceId(), HttpStatus.NOT_FOUND, BookmarkResponseCode.BOOKMARK_02, body)
    }
}
