package com.example.tleapplication.domain.bookmark

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BookmarkRepository {
    fun findBookmarkByUserAndNews(userId: Long, newsId: Long): Bookmark?
    fun findAllBookmarks(userId: Long, pageable: Pageable): Page<Bookmark>
    fun save(bookmark: Bookmark, user: User, news: News): Bookmark
    fun delete(id: Long)
}