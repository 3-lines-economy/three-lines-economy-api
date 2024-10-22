package com.example.tleapplication.domain.scrap

data class Scrap(
    var title: String?,
    var summary: String?,
    var what: String?,
    var why: String?,
    var how: String?,
    var digitize: String?,
    var insight: String?,
    var addition: String?,
    var application: String?,
    var link: String?,
    var userId: Long? = null,
    var newsId: Long? = null,
    var id: Long? = null
) {
}