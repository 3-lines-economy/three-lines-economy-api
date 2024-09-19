package com.example.tleapplication.domain.news

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class NewsService(
    private val newsRepository: NewsRepository
) {
    companion object {
        const val PAGE_SIZE = 10
    }

    fun registerNews(news: News) {
        newsRepository.save(news)
    }

    fun getNewsByCategory(category: Category?, page: Int): List<News> {
        val pageable = PageRequest.of(page - 1, PAGE_SIZE)

        return if (category != null) {
            newsRepository.findNewsByCategory(category, pageable)
        } else {
            newsRepository.findAllNews(pageable)
        }
    }

    fun getNewsByDate(date: LocalDate?, page: Int): List<News> {
        val pageable = PageRequest.of(page - 1, PAGE_SIZE)
        val targetDate = date ?: LocalDate.now()

        return newsRepository.findNewsByDate(targetDate, pageable)
    }

    fun findNewsById(id: Long): News? {
        return newsRepository.findNewsById(id)
    }
}