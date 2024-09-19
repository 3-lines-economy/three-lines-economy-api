package com.example.tleapplication.application.news

import com.example.tleapplication.domain.news.Category
import com.example.tleapplication.domain.news.News
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class NewsResponse(
    val id: Long,
    val title: String,
    val category: Category,
    val what: String,
    val why: String,
    val how: String,
    @JsonFormat(pattern = "yyyy.MM.dd")
    val publishedAt: LocalDate
) {
    companion object {
        fun from(news: News): NewsResponse {
            return NewsResponse(
                id = news.id!!,
                title = news.title,
                category = news.category,
                what = news.what,
                why = news.why,
                how = news.how,
                publishedAt = news.publishedAt
            )
        }
    }
}