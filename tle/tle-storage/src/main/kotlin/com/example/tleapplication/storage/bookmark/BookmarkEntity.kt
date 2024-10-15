package com.example.tleapplication.storage.bookmark

import com.example.tleapplication.domain.bookmark.Bookmark
import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.user.User
import com.example.tleapplication.storage.common.BaseEntity
import com.example.tleapplication.storage.news.NewsEntity
import com.example.tleapplication.storage.user.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(
    name = "bookmark",
    indexes = [Index(name = "UK_bookmark_id", columnList = "bookmarks_id", unique = true)]
)
class BookmarkEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    val news: NewsEntity,
) : BaseEntity() {
    companion object {
        fun of(bookmark: Bookmark, user: User, news: News): BookmarkEntity {
            return BookmarkEntity(
                id = bookmark.id,
                user = UserEntity.from(user),
                news = NewsEntity.from(news)
            )
        }
    }

    fun toDomain(): Bookmark {
        return Bookmark(
            id = this.id,
            userId = this.user.id,
            newsId = this.news.id!!
        )
    }
}