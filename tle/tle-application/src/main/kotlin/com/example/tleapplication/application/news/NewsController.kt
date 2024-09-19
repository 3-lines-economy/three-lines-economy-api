package com.example.tleapplication.application.news

import com.example.tleapplication.domain.news.Category
import com.example.tleapplication.domain.news.NewsService
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.TleApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    @Operation(
        summary = "뉴스 조회",
        description = "뉴스 조회 API",
        responses = [
            ApiResponse(responseCode = "200", description = "뉴스 조회 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @GetMapping("/{news-id}")
    @ResponseStatus(HttpStatus.OK)
    fun getNews(
        @PathVariable("news-id") id: Long
    ): TleApiResponse<NewsResponse> {
        val news = newsService.getNews(id)
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            NewsResponse.from(news)
        )
    }

    @Operation(
        summary = "카테고리 기준 뉴스 조회",
        description = "뉴스 조회 API(전체 or 카테고리)",
        responses = [
            ApiResponse(responseCode = "200", description = "뉴스 조회 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @GetMapping("by-category")
    @ResponseStatus(HttpStatus.OK)
    fun getNewsByCategory(
        @Parameter(name = "category", description = "카테고리", required = false)
        @RequestParam(required = false) category: Category?,
        @Parameter(name = "page", description = "페이지 번호", required = true)
        @RequestParam(defaultValue = "1") page: Int
    ): TleApiResponse<NewsListResponse> {
        val newsList = newsService.getNewsByCategory(category, page)
        val newsResponseList = newsList.stream().map { NewsResponse.from(it) }.toList()
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            NewsListResponse(newsResponseList)
        )
    }

    @Operation(
        summary = "날짜 기준 뉴스 조회",
        description = "뉴스 조회 API(오늘 or 특정 날짜)",
        responses = [
            ApiResponse(responseCode = "200", description = "뉴스 조회 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @GetMapping("/by-date")
    @ResponseStatus(HttpStatus.OK)
    fun getNewsByDate(
        @Parameter(name = "date", description = "날짜(yyyy.MM.dd)", required = false)
        @RequestParam(required = false) date: String?,
        @Parameter(name = "page", description = "페이지 번호", required = true)
        @RequestParam(defaultValue = "1") page: Int
    ): TleApiResponse<NewsListResponse> {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val localDate = date?.let {
            LocalDate.parse(it, formatter)
        }
        val newsList = newsService.getNewsByDate(localDate, page)
        val newsResponseList = newsList.stream().map { NewsResponse.from(it) }.toList()
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            NewsListResponse(newsResponseList)
        )
    }

    @Operation(
        summary = "뉴스 검색",
        description = "뉴스 검색 API(title, what, why, how 기준)",
        responses = [
            ApiResponse(responseCode = "200", description = "뉴스 검색 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    fun searchNews(
        @Parameter(name = "keyword", description = "검색어", required = true)
        @RequestParam keyword: String,
        @Parameter(name = "page", description = "페이지 번호", required = true)
        @RequestParam(defaultValue = "1") page: Int
    ): TleApiResponse<NewsListResponse>  {
        val newsList = newsService.searchNewsByKeyword(keyword, page)
        val newsResponseList = newsList.stream().map { NewsResponse.from(it) }.toList()
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            NewsListResponse(newsResponseList)
        )
    }
}