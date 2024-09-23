package com.example.tleapplication.domain.bookmark

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.user.User

interface BookmarkRepository {
    fun findBookmarkByUserAndNews(userId: Long, newsId: Long): Bookmark?
    fun save(bookmark: Bookmark, user: User, news: News): Bookmark
    fun delete(id: Long)
}