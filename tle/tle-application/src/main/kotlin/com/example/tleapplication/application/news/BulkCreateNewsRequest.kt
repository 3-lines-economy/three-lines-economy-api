package com.example.tleapplication.application.news

import org.jetbrains.annotations.NotNull

data class BulkCreateNewsRequest(
    @field:NotNull
    val newsList: List<CreateNewsRequest>
)
