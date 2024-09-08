package com.example.tleapplication.support.response

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class TleApiResponse<T>(
    val traceId: String,
    val status: Int,
    val code: String,
    val timestamp: LocalDateTime,
    val body: T?,
) {
    companion object {
        fun <T> of(
            traceId: String,
            status: HttpStatus,
            code: String,
            body: T,
        ): TleApiResponse<T> = TleApiResponse(
            traceId = traceId,
            status = status.value(),
            code = code,
            timestamp = LocalDateTime.now(),
            body = body
        )

        fun <T> success(
            traceId: String,
            body: T
        ): TleApiResponse<T> = TleApiResponse(
            traceId = traceId,
            status = HttpStatus.OK.value(),
            code = CommonResponseCode.COMMON_00,
            timestamp = LocalDateTime.now(),
            body = body
        )

        fun <T> success(
            traceId: String,
            status: HttpStatus,
            body: T
        ): TleApiResponse<T> = TleApiResponse(
            traceId = traceId,
            status = status.value(),
            code = CommonResponseCode.COMMON_00,
            timestamp = LocalDateTime.now(),
            body = body
        )

        const val SUCCESS = "OK"
    }
}
