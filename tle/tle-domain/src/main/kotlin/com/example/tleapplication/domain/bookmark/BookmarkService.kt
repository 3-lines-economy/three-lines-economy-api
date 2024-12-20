package com.example.tleapplication.domain.bookmark

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.news.NewsService
import com.example.tleapplication.domain.user.User
import com.example.tleapplication.domain.user.UserService
import com.example.tleapplication.support.exception.bookmark.BookmarkAlreadyExistsException
import com.example.tleapplication.support.exception.news.NewsNotFoundException
import com.example.tleapplication.support.exception.user.UserNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookmarkService(
    private val bookmarkRepository: BookmarkRepository,
    private val userService: UserService,
    private val newsService: NewsService
) {
    @Transactional
    fun registerBookmark(bookmark: Bookmark): Bookmark {
        val existedBookmark = bookmarkRepository.findBookmarkByUserAndNews(bookmark.user.id, bookmark.news.id!!)
        if (existedBookmark != null) {
            throw BookmarkAlreadyExistsException()
        }

        val user = userService.findUserById(bookmark.user.id) ?: UserNotFoundException()
        val news = newsService.findNewsById(bookmark.news.id!!) ?: NewsNotFoundException()

        val bookmark = bookmarkRepository.save(bookmark, user as User, news as News)
        return bookmark
    }

    @Transactional
    fun deleteBookmark(id: Long) {
        bookmarkRepository.delete(id)
    }

    fun getAllBookmarks(userId: Long, page: Int): Page<Bookmark> {
        val pageable = PageRequest.of(page - 1, NewsService.PAGE_SIZE)

        return bookmarkRepository.findAllBookmarks(userId, pageable)
    }
}