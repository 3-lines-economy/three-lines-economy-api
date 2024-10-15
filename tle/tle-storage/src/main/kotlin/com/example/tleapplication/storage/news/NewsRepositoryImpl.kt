package com.example.tleapplication.storage.news

import com.example.tleapplication.domain.news.Category
import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.news.NewsRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Repository
class NewsRepositoryImpl(
    private val newsJpaRepository: NewsJpaRepository
) : NewsRepository {
    override fun save(news: News) {
        val newsEntity = NewsEntity.from(news)
        newsJpaRepository.save(newsEntity)
    }

    override fun findNewsByCategory(category: Category, pageable: Pageable): List<News> {
        return newsJpaRepository.findByCategory(category, pageable).content
            .stream().map { it.toDomain() }.toList()
    }

    override fun findAllNews(pageable: Pageable): List<News> {
        return newsJpaRepository.findAll(pageable).content
            .stream().map { it.toDomain() }.toList()
    }

    override fun findNewsByDate(date: LocalDate, pageable: Pageable): List<News> {
        return newsJpaRepository.findByPublishedAt(date, pageable).content
            .stream().map { it.toDomain() }.toList()
    }

    override fun searchNewsByKeyword(keyword: String, pageable: Pageable): List<News> {
        return newsJpaRepository.searchByKeyword(keyword, pageable).content
            .stream().map { it.toDomain() }.toList()
    }

    override fun findNewsById(id: Long): News? {
        val newsEntity = newsJpaRepository.findById(id).getOrNull()
        return newsEntity?.toDomain()
    }
}

interface NewsJpaRepository: JpaRepository<NewsEntity, Long> {
    fun findByCategory(category: Category?, pageable: Pageable): Page<NewsEntity>
    fun findByPublishedAt(publishedAt: LocalDate, pageable: Pageable): Page<NewsEntity>
    @Query("""
        SELECT n FROM NewsEntity n
        WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(n.what) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(n.why) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(n.how) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    fun searchByKeyword(@Param("keyword") keyword: String, pageable: Pageable): Page<NewsEntity>
}