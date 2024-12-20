package com.example.tleapplication.domain.scrap

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
interface ScrapRepository {
    fun save(scrap: Scrap, user: User, news: News): Scrap
    fun findScrapByUserAndNews(userId: Long, newsId: Long): Scrap?
    fun findScrapById(id: Long): Scrap
    fun findAllScraps(userId: Long, pageable: Pageable): Page<Scrap>
    fun update(scrap: Scrap): Scrap
    fun delete(id: Long)
}