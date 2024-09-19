package com.example.tleapplication.application.news

import com.example.tleapplication.domain.news.NewsService
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.TleApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/news")
@Tag(name = "뉴스 API", description = "뉴스 API")
class NewsController(
    private val newsService: NewsService,
    private val traceIdResolver: TraceIdResolver
) {
    @Operation(hidden = true)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerNews(
        @Valid @RequestBody request: CreateNewsRequest
    ): TleApiResponse<Unit?> {
        val news = request.toDomain()
        newsService.registerNews(news)
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.CREATED,
            null
        )
    }
}