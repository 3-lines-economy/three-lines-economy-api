package com.example.tleapplication.application.scrap

import com.example.tleapplication.domain.scrap.Scrap
import org.springframework.data.domain.Page

data class ScrapListResponse(
    val totalPages: Int,
    val currentPage: Int,
    val totalElements: Long,
    val scrapList: List<ScrapResponse>
) {
    companion object {
        fun from(scrapPage: Page<Scrap>): ScrapListResponse {
            return ScrapListResponse(
                totalPages = scrapPage.totalPages,
                currentPage = scrapPage.number + 1,
                totalElements = scrapPage.totalElements,
                scrapList = scrapPage.content.map { ScrapResponse.from(it) }
            )
        }
    }
}