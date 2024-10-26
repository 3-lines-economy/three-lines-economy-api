package com.example.tleapplication.domain.news

import java.time.LocalDateTime

data class News(
    var title: String,
    var content: String,
    var category: Category,
    var link: String,
    var what: String,
    var why: String,
    var how: String,
    var publishedAt: LocalDateTime,
    var id: Long? = null
) {
}