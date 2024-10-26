package com.example.tleapplication.storage.news

import com.example.tleapplication.domain.news.Category
import com.example.tleapplication.domain.news.News
import com.example.tleapplication.storage.common.BaseEntity
import jakarta.persistence.*
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

    @Column(length = 2048, name = "title", columnDefinition = "TEXT")
    var title: String,

    @Column(length = 2048, name = "content", columnDefinition = "TEXT")
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