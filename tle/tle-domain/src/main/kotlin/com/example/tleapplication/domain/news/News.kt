package com.example.tleapplication.domain.news

import java.time.LocalDate

class News(
    var title: String,
    var category: Category,
    var link: String,
    var what: String,
    var why: String,
    var how: String,
    var publishedAt: LocalDate,
    var id: Long? = null
) {
}