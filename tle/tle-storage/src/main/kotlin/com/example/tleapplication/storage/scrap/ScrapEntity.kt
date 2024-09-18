package com.example.tleapplication.storage.scrap

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.scrap.Scrap
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
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(
    name = "scrap",
    indexes = [Index(name = "UK_scrap_id", columnList = "scrap_id", unique = true)]
)
@SQLRestriction("deleted_at is NULL")
class ScrapEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "scrap_id")
    val id: Long?,

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
    companion object {
        fun of(scrap: Scrap, user: User, news: News): ScrapEntity {
            return ScrapEntity(
                id = scrap.id,
                title = scrap.title!!,
                summary = scrap.summary!!,
                what = scrap.what!!,
                why = scrap.why!!,
                how = scrap.how!!,
                digitize = scrap.digitize!!,
                insight = scrap.insight!!,
                addition = scrap.addition!!,
                application = scrap.application!!,
                link = scrap.link!!,
                user = UserEntity.from(user),
                news = NewsEntity.from(news)
            )
        }
    }

    fun toDomain(): Scrap {
        return Scrap(
            id = this.id,
            title = this.title,
            summary = this.summary,
            what = this.what,
            why = this.why,
            how = this.how,
            digitize = this.digitize,
            insight = this.insight,
            addition = this.addition,
            application = this.application,
            link = this.link,
            userId = this.user.id,
            newsId = this.news.id
        )
    }
}