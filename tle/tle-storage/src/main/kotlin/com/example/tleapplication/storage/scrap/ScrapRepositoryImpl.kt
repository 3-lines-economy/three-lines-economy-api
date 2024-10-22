package com.example.tleapplication.storage.scrap

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.scrap.Scrap
import com.example.tleapplication.domain.scrap.ScrapRepository
import com.example.tleapplication.domain.user.User
import com.example.tleapplication.support.exception.scrap.ScrapNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ScrapRepositoryImpl(
    private val scrapJpaRepository: ScrapJpaRepository
) : ScrapRepository {
    override fun save(scrap: Scrap, user: User, news: News): Scrap {
        val scrapEntity = ScrapEntity.of(scrap, user, news)
        val result = scrapJpaRepository.save(scrapEntity)
        return result.toDomain()
    }

    override fun findScrapByUserAndNews(userId: Long, newsId: Long): Scrap? {
        val scrap = scrapJpaRepository.findByUserIdAndNewsId(userId, newsId)?.toDomain()
        return scrap
    }

    override fun findScrapById(id: Long): Scrap {
        val scrapEntity = scrapJpaRepository.findById(id).orElseThrow { ScrapNotFoundException() }
        return scrapEntity.toDomain()
    }

    override fun update(scrap: Scrap): Scrap {
        val scrapEntity = scrapJpaRepository.findById(scrap.id!!).orElseThrow { ScrapNotFoundException() }

        scrap.title?.let { scrapEntity.title = it }
        scrap.summary?.let { scrapEntity.summary = it }
        scrap.what?.let { scrapEntity.what = it }
        scrap.why?.let { scrapEntity.why = it }
        scrap.how?.let { scrapEntity.how = it }
        scrap.digitize?.let { scrapEntity.digitize = it }
        scrap.insight?.let { scrapEntity.insight = it }
        scrap.addition?.let { scrapEntity.addition = it }
        scrap.link?.let { scrapEntity.link = it }

        return scrapJpaRepository.save(scrapEntity).toDomain()
    }

    override fun delete(id: Long) {
        val scrapEntity = scrapJpaRepository.findById(id).orElseThrow { ScrapNotFoundException() }
        scrapEntity.deletedAt = LocalDateTime.now()
        scrapJpaRepository.save(scrapEntity)
    }
}

interface ScrapJpaRepository : JpaRepository<ScrapEntity, Long> {
    @Query("SELECT s FROM ScrapEntity s WHERE s.user.id = :userId AND s.news.id = :newsId")
    fun findByUserIdAndNewsId(@Param("userId") userId: Long, @Param("newsId") newsId: Long): ScrapEntity?
}