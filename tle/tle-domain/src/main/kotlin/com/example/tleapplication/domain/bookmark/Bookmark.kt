package com.example.tleapplication.domain.bookmark

data class Bookmark(
    val userId: Long,
    val newsId: Long,
    val id: Long? = null
) {
}
