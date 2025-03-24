package com.example.tleapplication.domain.news

import com.example.tleapplication.support.exception.news.NewsNotFoundException
import org.springframework.cglib.core.Local
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class NewsService(
    private val newsRepository: NewsRepository
) {
    companion object {
        const val PAGE_SIZE = 10
    }

    @Transactional
    fun registerNews(news: News) {
        newsRepository.save(news)
    }

    @Transactional
    fun registerBulkNews(bulkNews: List<News>) {
        newsRepository.saveAll(bulkNews)
    }

    fun getNews(id: Long): News {
        return findNewsById(id) ?: throw NewsNotFoundException()
    }

    fun getNewsByConditions(category: Category, date: LocalDate?, page: Int): Page<News> {
        val pageable = PageRequest.of(page - 1, PAGE_SIZE)
        val targetDate = date ?: LocalDate.now()

        return newsRepository.findNewsByConditions(category, targetDate, pageable)
    }

    fun getNewsByCategory(category: Category, page: Int): Page<News> {
        val pageable = PageRequest.of(page - 1, PAGE_SIZE)

        return newsRepository.findNewsByCategory(category, pageable)
    }

    fun getNewsByDate(date: LocalDate?, page: Int): Page<News> {
        val pageable = PageRequest.of(page - 1, PAGE_SIZE)
        val targetDate = date ?: LocalDate.now()

        return newsRepository.findNewsByDate(targetDate, pageable)
    }

    fun getAllNews(page: Int): Page<News> {
        val pageable = PageRequest.of(page - 1, PAGE_SIZE)

        return newsRepository.findAllNews(pageable)
    }

    fun searchNewsByKeyword(keyword: String, page: Int): Page<News> {
        val pageable = PageRequest.of(page - 1, PAGE_SIZE)
        return newsRepository.searchNewsByKeyword(keyword, pageable)
    }

    fun findNewsById(id: Long): News? {
        return newsRepository.findNewsById(id)
    }
}