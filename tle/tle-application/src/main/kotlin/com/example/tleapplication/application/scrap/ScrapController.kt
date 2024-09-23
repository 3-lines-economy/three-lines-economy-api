package com.example.tleapplication.application.scrap

import com.example.tleapplication.domain.scrap.ScrapService
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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/scrap")
@Tag(name = "경신스 API", description = "경신스 API")
class ScrapController(
    private val scrapService: ScrapService,
    private val traceIdResolver: TraceIdResolver
) {
    @Operation(
        summary = "경신스 작성",
        description = "경신스 작성 API",
        responses = [
            ApiResponse(responseCode = "201", description = "경신스 작성 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createScrap(
        @Auth authInfo: AuthInfo,
        @Valid @RequestBody request: CreateScrapRequest
    ): TleApiResponse<ScrapResponse> {
        val scrap = request.toDomain(authInfo.userId)
        val newScrap = scrapService.createScrap(scrap)
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.CREATED,
            ScrapResponse.from(newScrap)
        )
    }

    @Operation(
        summary = "경신스 조회",
        description = "경신스 조회 API",
        responses = [
            ApiResponse(responseCode = "200", description = "경신스 조회 성공"),
            ApiResponse(responseCode = "404", description = "경신스를 찾을 수 없음"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @GetMapping("/{scrap-id}")
    @ResponseStatus(HttpStatus.OK)
    fun getScrap(
        @PathVariable("scrap-id") id: Long
    ): TleApiResponse<ScrapResponse> {
        val scrap = scrapService.getScrap(id)
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            ScrapResponse.from(scrap)
        )
    }

    @Operation(
        summary = "경신스 수정",
        description = "경신스 수정 API",
        responses = [
            ApiResponse(responseCode = "200", description = "경신스 수정 성공"),
            ApiResponse(responseCode = "404", description = "경신스를 찾을 수 없음"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @PatchMapping("/{scrap-id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateScrap(
        @PathVariable("scrap-id") id: Long,
        @Valid @RequestBody request: UpdateScrapRequest
    ): TleApiResponse<ScrapResponse> {
        val scrap = scrapService.updateScrap(request.toDomain(id))
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            ScrapResponse.from(scrap)
        )
    }

    @Operation(
        summary = "경신스 삭제",
        description = "경신스 삭제 API",
        responses = [
            ApiResponse(responseCode = "200", description = "경신스 삭제 성공"),
            ApiResponse(responseCode = "404", description = "경신스를 찾을 수 없음"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @DeleteMapping("/{scrap-id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteScrap(
        @PathVariable("scrap-id") id: Long
    ): TleApiResponse<Unit?>  {
        scrapService.deleteScrap(id)
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            null
        )
    }
}