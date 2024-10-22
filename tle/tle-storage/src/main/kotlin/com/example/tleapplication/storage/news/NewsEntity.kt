package com.example.tleapplication.storage.news

import com.example.tleapplication.domain.news.Category
import com.example.tleapplication.domain.news.News
import com.example.tleapplication.storage.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(
    name = "news",
    indexes = [Index(name = "UK_news_id", columnList = "news_id", unique = true)]
)
class NewsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    val id: Long?,

    @Column(length = 512, name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Enumerated(EnumType.STRING)
    var category: Category,

    @Column(length = 2048, name = "link")
    var link: String,

    @Column(name = "what")
    var what: String,

    @Column(name = "why")
    var why: String,

    @Column(name = "how")
    var how: String,

    @Column(name = "published_at")
    var publishedAt: LocalDateTime
) : BaseEntity() {
    companion object {
        fun from(news: News): NewsEntity {
            return NewsEntity(
                id = news.id,
                title = news.title,
                content = news.content,
                category = news.category,
                link = news.link,
                what = news.what,
                why = news.why,
                how = news.how,
                publishedAt = news.publishedAt
            )
        }
    }

    fun toDomain(): News {
        return News(
            id = this.id,
            title = this.title,
            content = this.content,
            category = this.category,
            link = this.link,
            what = this.what,
            why = this.why,
            how = this.how,
            publishedAt = this.publishedAt
        )
    }
}