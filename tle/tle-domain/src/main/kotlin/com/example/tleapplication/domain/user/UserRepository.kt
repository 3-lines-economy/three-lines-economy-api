package com.example.tleapplication.domain.user

import org.springframework.stereotype.Repository

@Repository
interface UserRepository {
    fun save(user: User): User
    fun findUserById(id: Long): User?
    fun update(user: User): User
}