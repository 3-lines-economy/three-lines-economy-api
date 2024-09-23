package com.example.tleapplication.application.bookmark

import com.example.tleapplication.domain.bookmark.Bookmark
import com.example.tleapplication.domain.bookmark.BookmarkService
import com.example.tleapplication.support.logging.TraceIdResolver
import com.example.tleapplication.support.response.TleApiResponse
import com.example.tleapplication.support.security.Auth
import com.example.tleapplication.support.security.AuthInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bookmarks")
@Tag(name = "뉴스 북마크 API", description = "뉴스 북마크 API")
class BookmarkController(
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
        val bookmark = Bookmark(authInfo.userId, newsId)
        val newBookmark = bookmarkService.registerBookmark(bookmark)
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.CREATED,
            BookmarkResponse.from(newBookmark)
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
    ): TleApiResponse<Unit?>   {
        bookmarkService.deleteBookmark(id)
        return TleApiResponse.success(
            traceIdResolver.getTraceId(),
            HttpStatus.OK,
            null
        )
    }
}