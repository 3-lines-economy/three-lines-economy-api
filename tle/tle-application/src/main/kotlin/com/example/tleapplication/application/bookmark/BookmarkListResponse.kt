package com.example.tleapplication.application.bookmark

import com.example.tleapplication.domain.bookmark.Bookmark
import org.springframework.data.domain.Page

data class BookmarkListResponse(
    val totalPages: Int,
    val currentPage: Int,
    val totalElements: Long,
    val bookmarkList: List<BookmarkResponse>
) {
    companion object {
        fun from(bookmarkPage: Page<Bookmark>): BookmarkListResponse {
            return BookmarkListResponse(
                totalPages =  bookmarkPage.totalPages,
                currentPage = bookmarkPage.number + 1,
                totalElements = bookmarkPage.totalElements,
                bookmarkList = bookmarkPage.content.map { BookmarkResponse.from(it) }
            )
        }
    }
}