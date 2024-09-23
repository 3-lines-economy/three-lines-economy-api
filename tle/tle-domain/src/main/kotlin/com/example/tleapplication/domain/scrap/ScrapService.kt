package com.example.tleapplication.domain.scrap

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.news.NewsService
import com.example.tleapplication.domain.user.User
import com.example.tleapplication.domain.user.UserService
import com.example.tleapplication.support.exception.news.NewsNotFoundException
import com.example.tleapplication.support.exception.scrap.ScrapAlreadyExistsException
import com.example.tleapplication.support.exception.user.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class ScrapService(
    private val scrapRepository: ScrapRepository,
    private val userService: UserService,
    private val newsService: NewsService
) {
    fun createScrap(scrap: Scrap): Scrap {
        val existedScrap = scrapRepository.findScrapByUserAndNews(scrap.userId!!, scrap.newsId!!)
        if (existedScrap != null) {
            throw ScrapAlreadyExistsException()
        }

        val user = userService.findUserById(scrap.userId!!) ?: UserNotFoundException()
        val news = newsService.findNewsById(scrap.newsId!!) ?: NewsNotFoundException()

        val scrap = scrapRepository.save(scrap, user as User, news as News)
        return scrap
    }

    fun getScrap(id: Long): Scrap {
        val scrap = scrapRepository.findScrapById(id)
        return scrap
    }

    fun updateScrap(scrap: Scrap): Scrap {
        val scrap = scrapRepository.update(scrap)
        return scrap
    }

    fun deleteScrap(id: Long) {
        scrapRepository.delete(id)
    }
}