package com.example.tleapplication.application.news

import com.example.tleapplication.domain.news.News
import org.springframework.data.domain.Page

data class NewsListResponse(
    val totalPages: Int,
    val currentPage: Int,
    val totalElements: Long,
    val newsList: List<NewsResponse>
) {
    companion object {
        fun from(newsPage: Page<News>): NewsListResponse {
            return NewsListResponse(
                totalPages = newsPage.totalPages,
                currentPage = newsPage.number + 1,
                totalElements = newsPage.totalElements,
                newsList = newsPage.content.map { NewsResponse.from(it)}
            )
        }
    }
}