package com.example.tleapplication.storage.bookmark

import com.example.tleapplication.domain.bookmark.Bookmark
import com.example.tleapplication.domain.bookmark.BookmarkRepository
import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.user.User
import com.example.tleapplication.support.exception.bookmark.BookmarkNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
class BookmarkRepositoryImpl(
    private val bookmarkJpaRepository: BookmarkJpaRepository
) : BookmarkRepository {
    override fun findBookmarkByUserAndNews(userId: Long, newsId: Long): Bookmark? {
        val bookmark = bookmarkJpaRepository.findByUserIdAndNewsId(userId, newsId)?.toDomain()
        return bookmark
    }

    override fun save(bookmark: Bookmark, user: User, news: News): Bookmark {
        val bookmarkEntity = BookmarkEntity.of(bookmark, user, news)
        val result = bookmarkJpaRepository.save(bookmarkEntity)
        return result.toDomain()
    }

    override fun delete(id: Long) {
        val bookmarkEntity = bookmarkJpaRepository.findById(id).orElseThrow { BookmarkNotFoundException() }
        bookmarkJpaRepository.delete(bookmarkEntity)
    }
}

interface BookmarkJpaRepository: JpaRepository<BookmarkEntity, Long> {
    @Query("SELECT b FROM BookmarkEntity b WHERE b.user.id = :userId AND b.news.id = :newsId")
    fun findByUserIdAndNewsId(@Param("userId") userId: Long, @Param("newsId") newsId: Long): BookmarkEntity?
}