package com.example.tleapplication.domain.bookmark

import com.example.tleapplication.domain.news.News
import com.example.tleapplication.domain.user.User

data class Bookmark(
    val user: User,
    val news: News,
    val id: Long? = null
)
