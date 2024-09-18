package com.example.tleapplication.domain.news

import java.time.LocalDateTime

class News(
    var id: Long?,
    var title: String,
    var category: Category,
    var link: String,
    var what: String,
    var why: String,
    var how: String,
    var publishedAt: LocalDateTime
) {
}