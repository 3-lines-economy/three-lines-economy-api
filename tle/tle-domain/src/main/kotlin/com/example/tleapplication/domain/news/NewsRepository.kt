package com.example.tleapplication.domain.news

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface NewsRepository {
    fun save(news: News)
    fun saveAll(bulkNews: List<News>)
    fun findNewsByCategory(category: Category, pageable: Pageable): Page<News>
    fun findAllNews(pageable: Pageable): Page<News>
    fun findNewsByDate(date: LocalDate, pageable: Pageable): Page<News>
    fun searchNewsByKeyword(keyword: String, pageable: Pageable): Page<News>
    fun findNewsById(id: Long): News?
}