package com.example.tleapplication.domain.news

import org.springframework.stereotype.Repository

@Repository
interface NewsRepository {
    fun save(news: News)
    fun findNewsById(id: Long): News?
}