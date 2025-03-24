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
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/news")
@Tag(name = "뉴스 API", description = "뉴스 API")
class NewsController(
    private val newsService: NewsService,
    private val traceIdResolver: TraceIdResolver
) {
    @Operation(
        summary = "뉴스 개별 등록",
        description = "뉴스 개별 등록 API",
        responses = [
            ApiResponse(responseCode = "201", description = "뉴스 등록 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerNews(
        @Valid @RequestBody request: CreateNewsRequest
    ): TleApiResponse<String> {
        val news = request.toDomain()
        newsService.registerNews(news)
        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.CREATED,
            body = TleApiResponse.SUCCESS,
        )
    }

    @Operation(
        summary = "뉴스 벌크 등록",
        description = "뉴스 벌크 등록 API",
        responses = [
            ApiResponse(responseCode = "201", description = "뉴스 등록 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerBulkNews(
        @Valid @RequestBody request: BulkCreateNewsRequest
    ): TleApiResponse<String> {
        val bulkNews = request.newsList.stream().map { it.toDomain() }.toList()
        newsService.registerBulkNews(bulkNews)
        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.CREATED,
            body = TleApiResponse.SUCCESS,
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
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.OK,
            body = NewsResponse.from(news)
        )
    }

    @Operation(
        summary = "조건 기준 뉴스 전체 조회",
        description = "조건 기준 뉴스 전체 조회 API",
        responses = [
            ApiResponse(responseCode = "200", description = "뉴스 조회 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    fun getNewsByConditions(
        @Parameter(name = "category", description = "카테고리", required = true)
        @RequestParam(required = true) category: Category,
        @Parameter(name = "date", description = "날짜(yyyy.MM.dd)", required = false)
        @RequestParam(required = false) date: String?,
        @Parameter(name = "page", description = "페이지 번호", required = true)
        @RequestParam(defaultValue = "1") page: Int
    ): TleApiResponse<NewsListResponse> {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val formattedDate = date?.let {
            LocalDate.parse(it, formatter)
        }

        val newsPage = newsService.getNewsByConditions(category, formattedDate, page)

        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.OK,
            body = NewsListResponse.from(newsPage)
        )
    }

    @Operation(
        summary = "카테고리 기준 뉴스 조회",
        description = "카테고리 기준 뉴스 조회 API",
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
        @Parameter(name = "category", description = "카테고리", required = true)
        @RequestParam(required = true) category: Category,
        @Parameter(name = "date", description = "날짜(yyyy.MM.dd)", required = false)
        @RequestParam(required = false) date: String?,
        @Parameter(name = "page", description = "페이지 번호", required = true)
        @RequestParam(defaultValue = "1") page: Int
    ): TleApiResponse<NewsListResponse> {
        val newsPage = newsService.getNewsByCategory(category, page)
        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.OK,
            body = NewsListResponse.from(newsPage)
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
        val formattedDate = date?.let {
            LocalDate.parse(it, formatter)
        }
        val newsPage= newsService.getNewsByDate(formattedDate, page)
        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.OK,
            body = NewsListResponse.from(newsPage)
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
        val newsPage = newsService.searchNewsByKeyword(keyword, page)
        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.OK,
            body = NewsListResponse.from(newsPage)
        )
    }

    @Operation(
        summary = "뉴스 전체 조회",
        description = "뉴스 전체 조회 API",
        responses = [
            ApiResponse(responseCode = "200", description = "뉴스 조회 성공"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllNews(
        @Parameter(name = "page", description = "페이지 번호", required = true)
        @RequestParam(defaultValue = "1") page: Int
    ): TleApiResponse<NewsListResponse> {
        val newsPage = newsService.getAllNews(page)
        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.OK,
            body = NewsListResponse.from(newsPage)
        )
    }
}