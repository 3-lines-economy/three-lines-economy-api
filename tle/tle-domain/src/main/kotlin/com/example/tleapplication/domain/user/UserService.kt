package com.example.tleapplication.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    fun findUserById(id: Long): User? {
        return userRepository.findUserById(id)
    }
}