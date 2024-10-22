package com.example.tleapplication.application.scrap

import com.example.tleapplication.domain.scrap.Scrap
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

data class CreateScrapRequest(
    @field:NotNull
    @Schema(description = "헤드라인")
    val title: String,

    @field:NotNull
    @Schema(description = "한 줄 요약")
    val summary: String,

    @field:NotNull
    @Schema(description = "세줄 요약 > what")
    val what: String,

    @field:NotNull
    @Schema(description = "세줄 요약 > why")
    val why: String,

    @field:NotNull
    @Schema(description = "세줄 요약 > how")
    val how: String,

    @field:NotNull
    @Schema(description = "기사 내용의 수치화")
    val digitize: String,

    @field:NotNull
    @Schema(description = "기사 내용 인사이트")
    val insight: String,

    @field:NotNull
    @Schema(description = "추가조사 내용")
    val addition: String,

    @field:NotNull
    @Schema(description = "적용할 점")
    val application: String,

    @field:NotNull
    @Schema(description = "연관기사 링크")
    val link: String,

    @field:NotNull
    @Schema(description = "연관기사 id")
    val newsId: Long,
) {
    fun toDomain(userId: Long): Scrap {
        return Scrap(
            title = this.title,
            summary = this.summary,
            what = this.what,
            why = this.why,
            how = this.how,
            digitize = this.digitize,
            insight = this.insight,
            addition = this.addition,
            application = this.application,
            link = this.link,
            userId = userId,
            newsId = this.newsId
        )
    }
}
