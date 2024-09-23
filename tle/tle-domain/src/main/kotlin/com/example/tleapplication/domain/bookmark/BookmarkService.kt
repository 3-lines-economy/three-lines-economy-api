package com.example.tleapplication.domain.bookmark

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.news.NewsService
import com.example.tleapplication.domain.user.User
import com.example.tleapplication.domain.user.UserService
import com.example.tleapplication.support.exception.bookmark.BookmarkAlreadyExistsException
import com.example.tleapplication.support.exception.news.NewsNotFoundException
import com.example.tleapplication.support.exception.user.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class BookmarkService(
    private val bookmarkRepository: BookmarkRepository,
    private val userService: UserService,
    private val newsService: NewsService
) {
    fun registerBookmark(bookmark: Bookmark): Bookmark {
        val existedBookmark = bookmarkRepository.findBookmarkByUserAndNews(bookmark.userId, bookmark.newsId)
        if (existedBookmark != null) {
            throw BookmarkAlreadyExistsException()
        }

        val user = userService.findUserById(bookmark.userId) ?: UserNotFoundException()
        val news = newsService.findNewsById(bookmark.newsId) ?: NewsNotFoundException()

        val bookmark = bookmarkRepository.save(bookmark, user as User, news as News)
        return bookmark
    }

    fun deleteBookmark(id: Long) {
        bookmarkRepository.delete(id)
    }
}