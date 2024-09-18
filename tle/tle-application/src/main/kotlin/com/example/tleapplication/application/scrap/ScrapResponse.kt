package com.example.tleapplication.application.scrap

import com.example.tleapplication.domain.scrap.Scrap

data class ScrapResponse(
    val id: Long,
    val title: String,
    val summary: String,
    val what: String,
    val why: String,
    val how: String,
    val digitize: String,
    val insight: String,
    val addition: String,
    val application: String,
    val link: String
) {
    companion object {
        fun from(scrap: Scrap): ScrapResponse {
            return ScrapResponse (
                id = scrap.id!!,
                title = scrap.title!!,
                summary = scrap.summary!!,
                what = scrap.what!!,
                why = scrap.why!!,
                how = scrap.how!!,
                digitize = scrap.digitize!!,
                insight = scrap.insight!!,
                addition = scrap.addition!!,
                application = scrap.application!!,
                link = scrap.link!!
            )
        }
    }
}