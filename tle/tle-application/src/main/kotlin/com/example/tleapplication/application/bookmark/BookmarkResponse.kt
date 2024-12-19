package com.example.tleapplication.application.bookmark

import com.example.tleapplication.domain.bookmark.Bookmark
import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.user.User

data class BookmarkResponse(
    val id: Long,
    val user: User,
    val news: News,
) {
    companion object {
        fun from(bookmark: Bookmark): BookmarkResponse {
            return BookmarkResponse(
                id = bookmark.id!!,
                user = bookmark.user,
                news = bookmark.news
            )
        }
    }
}