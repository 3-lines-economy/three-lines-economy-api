package com.example.tleapplication.domain.news

import org.springframework.stereotype.Repository

@Repository
interface NewsRepository {
    fun findNewsById(id: Long): News?
}