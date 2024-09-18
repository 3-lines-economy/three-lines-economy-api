package com.example.tleapplication.domain.news

import org.springframework.stereotype.Service

@Service
class NewsService(
    private val newsRepository: NewsRepository
) {
    fun findNewsById(id: Long): News? {
        return newsRepository.findNewsById(id)
    }
}