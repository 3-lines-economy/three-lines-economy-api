package com.example.tleapplication.domain.user

import org.springframework.stereotype.Repository

@Repository
interface UserRepository {
    fun save(user: User): User
    fun findUserById(id: Long): User?
    fun updateRefreshToken(refreshToken: String, id: Long)
}