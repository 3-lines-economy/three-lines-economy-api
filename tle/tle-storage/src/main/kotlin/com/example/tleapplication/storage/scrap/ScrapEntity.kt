package com.example.tleapplication.storage.scrap

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
    name = "scrap",
    indexes = [Index(name = "UK_scrap_id", columnList = "scrap_id", unique = true)]
)
class ScrapEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "scrap_id")
    val id: Long,

    @field:Column(length = 512, name = "title")
    var title: String,

    @field:Column(name = "summary")
    var summary: String,

    @field:Column(name = "what")
    var what: String,

    @field:Column(name = "why")
    var why: String,

    @field:Column(name = "how")
    var how: String,

    @field:Column(name = "digitize")
    var digitize: String,

    @field:Column(name = "insight")
    var insight: String,

    @field:Column(name = "addition")
    var addition: String,

    @field:Column(name = "application")
    var application: String,

    @field:Column(length = 2048, name = "link")
    var link: String,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    val user: UserEntity,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "news_id")
    val news: NewsEntity,
) : BaseEntity() {
}