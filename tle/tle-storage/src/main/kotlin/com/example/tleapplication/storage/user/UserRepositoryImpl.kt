package com.example.tleapplication.storage.user

import com.example.tleapplication.domain.user.User
import com.example.tleapplication.domain.user.UserRepository
import com.example.tleapplication.support.exception.UserNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {
    override fun save(user: User): User {
        val userEntity = UserEntity.from(user)
        val result = userJpaRepository.save(userEntity)
        return result.toDomain()
    }

    override fun findUserById(id: Long): User? {
        val userEntity = userJpaRepository.findById(id).getOrNull()
        return userEntity?.toDomain()
    }

    override fun updateRefreshToken(refreshToken: String, id: Long) {
        val userEntity = userJpaRepository.findById(id).orElseThrow{ throw UserNotFoundException() }
        userEntity.refreshToken = refreshToken
        userJpaRepository.save(userEntity)
    }
}

interface UserJpaRepository : JpaRepository<UserEntity, Long>