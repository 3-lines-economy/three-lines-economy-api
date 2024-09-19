package com.example.tleapplication.domain.news

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface NewsRepository {
    fun save(news: News)
    fun findNewsByCategory(category: Category, pageable: Pageable): List<News>
    fun findAllNews(pageable: Pageable): List<News>
    fun findNewsByDate(date: LocalDate, pageable: Pageable): List<News>
    fun findNewsById(id: Long): News?
}