package com.example.tleapplication.storage.news

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.news.NewsRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class NewsRepositoryImpl(
    private val newsJpaRepository: NewsJpaRepository
) : NewsRepository {
    override fun findNewsById(id: Long): News? {
        val newsEntity = newsJpaRepository.findById(id).getOrNull()
        return newsEntity?.toDomain()
    }
}

interface NewsJpaRepository: JpaRepository<NewsEntity, Long>