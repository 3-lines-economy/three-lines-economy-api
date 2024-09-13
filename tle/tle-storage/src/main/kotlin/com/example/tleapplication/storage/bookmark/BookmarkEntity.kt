package com.example.tleapplication.storage.bookmark

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
    name = "bookmarks",
    indexes = [Index(name = "UK_bookmarks_id", columnList = "bookmarks_id", unique = true)]
)
class BookmarkEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "bookmarks_id")
    val id: Long,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    val user: UserEntity,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "news_id")
    val news: NewsEntity,
) : BaseEntity() {
}