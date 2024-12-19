package com.example.tleapplication.application.bookmark

import com.example.tleapplication.domain.bookmark.Bookmark
import com.example.tleapplication.domain.bookmark.BookmarkService
import com.example.tleapplication.domain.news.NewsService
import com.example.tleapplication.domain.user.UserService
import com.example.tleapplication.support.exception.news.NewsNotFoundException
import com.example.tleapplication.support.exception.user.UserNotFoundException
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.TleApiResponse
import com.example.tleapplication.support.security.Auth
import com.example.tleapplication.support.security.AuthInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookmarks")
@Tag(name = "뉴스 북마크 API", description = "뉴스 북마크 API")
class BookmarkController(
    private val userService: UserService,
    private val newsService: NewsService,
    private val bookmarkService: BookmarkService,
    private val traceIdResolver: TraceIdResolver
) {
    @Operation(
        summary = "뉴스 북마크 등록",
        description = "뉴스 북마크 등록 API",
        responses = [
            ApiResponse(responseCode = "201", description = "뉴스 북마크 등록 성공"),
            ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음"),
            ApiResponse(responseCode = "404", description = "뉴스를 찾을 수 없음"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerBookmark(
        @Auth authInfo: AuthInfo,
        @RequestParam("news-id") newsId: Long
    ): TleApiResponse<BookmarkResponse> {
        val user = userService.findUserById(authInfo.userId) ?: throw UserNotFoundException()
        val news = newsService.findNewsById(newsId) ?: throw NewsNotFoundException()

        val bookmark = Bookmark(user, news)

        val newBookmark = bookmarkService.registerBookmark(bookmark)
        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.CREATED,
            body = BookmarkResponse.from(newBookmark)
        )
    }

    @Operation(
        summary = "뉴스 북마크 삭제",
        description = "뉴스 북마크 삭제 API",
        responses = [
            ApiResponse(responseCode = "200", description = "뉴스 북마크 삭제 성공"),
            ApiResponse(responseCode = "404", description = "뉴스 북마크를 찾을 수 없음"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @DeleteMapping("/{bookmark-id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteBookmark(
        @PathVariable("bookmark-id") id: Long
    ): TleApiResponse<String>   {
        bookmarkService.deleteBookmark(id)
        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.OK,
            body = TleApiResponse.SUCCESS,
        )
    }

    @Operation(
        summary = "북마크 전체 조회",
        description = "북마크 조회 API",
        responses = [
            ApiResponse(responseCode = "200", description = "북마크 조회 성공"),
            ApiResponse(responseCode = "404", description = "북마크를 찾을 수 없음"),
            ApiResponse(responseCode = "500", description = "Internal Server Error", content = arrayOf(
                Content(schema = Schema(hidden = true))
            )),
        ],
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllBookmarks(
        @Auth authInfo: AuthInfo,
        @Parameter(name = "page", description = "페이지 번호", required = true)
        @RequestParam(defaultValue = "1") page: Int
    ): TleApiResponse<BookmarkListResponse> {
        val bookmarks = bookmarkService.getAllBookmarks(authInfo.userId, page)
        val bookmarkResponseList = bookmarks.map { BookmarkResponse.from(it) }

        return TleApiResponse.success(
            traceId = traceIdResolver.getTraceId(),
            status = HttpStatus.OK,
            body = BookmarkListResponse(bookmarkResponseList)
        )
    }
}