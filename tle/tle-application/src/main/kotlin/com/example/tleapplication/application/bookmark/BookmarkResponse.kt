package com.example.tleapplication.application.bookmark

import com.example.tleapplication.domain.bookmark.Bookmark

data class BookmarkResponse(
    val id: Long
) {
    companion object {
        fun from(bookmark: Bookmark): BookmarkResponse {
            return BookmarkResponse(
                id = bookmark.id!!
            )
        }
    }
}