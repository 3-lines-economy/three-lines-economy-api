package com.example.tleapplication.application.news

import com.example.tleapplication.domain.news.Category
import com.example.tleapplication.domain.news.News
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CreateNewsRequest(
    @field:NotNull
    val title: String,

    @field:NotNull
    val category: Category,

    @field:NotNull
    val link: String,

    @field:NotNull
    val what: String,

    @field:NotNull
    val why: String,

    @field:NotNull
    val how: String,

    @field:NotNull
    @JsonFormat(pattern = "yyyy.MM.dd")
    val publishedAt: LocalDate
) {
    fun toDomain(): News {
        return News(
            title = this.title,
            category = this.category,
            link = this.link,
            what = this.what,
            why = this.why,
            how = this.how,
            publishedAt = this.publishedAt
        )
    }
}