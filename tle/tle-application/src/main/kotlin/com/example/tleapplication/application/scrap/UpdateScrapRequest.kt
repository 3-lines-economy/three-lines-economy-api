package com.example.tleapplication.application.scrap

import com.example.tleapplication.domain.scrap.Scrap
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateScrapRequest(
    @Schema(description = "헤드라인")
    val title: String?,

    @Schema(description = "한 줄 요약")
    val summary: String?,

    @Schema(description = "세줄 요약 > what")
    val what: String?,

    @Schema(description = "세줄 요약 > why")
    val why: String?,

    @Schema(description = "세줄 요약 > how")
    val how: String?,

    @Schema(description = "기사 내용의 수치화")
    val digitize: String?,

    @Schema(description = "기사 내용 인사이트")
    val insight: String?,

    @Schema(description = "추가조사 내용")
    val addition: String?,

    @Schema(description = "적용할 점")
    val application: String?,

    @Schema(description = "연관기사 링크")
    val link: String?
) {
    fun toDomain(id: Long): Scrap {
        return Scrap(
            this.title,
            this.summary,
            this.what,
            this.why,
            this.how,
            this.digitize,
            this.insight,
            this.addition,
            this.application,
            this.link,
            null,
            null,
            id
        )
    }
}