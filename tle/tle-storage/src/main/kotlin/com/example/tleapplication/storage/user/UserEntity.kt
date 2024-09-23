package com.example.tleapplication.storage.user

import com.example.tleapplication.domain.user.User
import com.example.tleapplication.storage.common.BaseEntity
import com.example.tleapplication.domain.user.UserRoleEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "users",
    indexes = [Index(name = "UK_user_id", columnList = "user_id", unique = true)]
)
class UserEntity(
    @field:Id
//    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "user_id")
    val id: Long,

    @field:Column(length = 64, name = "email", unique = true)
    var email: String?,

    @field:Column(length = 64, name = "nick_name", unique = true)
    var nickName: String,

    @field:Column(length = 512, name = "kakao_access_token")
    var kakaoAccessToken: String?,

    @field:Column(length = 512, name = "refresh_token")
    var refreshToken: String?,

    @field:Column(length = 2048, name = "profile_image")
    var profileImage: String,

    @field:Enumerated(EnumType.STRING)
    val role: UserRoleEnum = UserRoleEnum.ROLE_USER
) : BaseEntity() {
    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                email = user.email,
                nickName = user.nickName,
                kakaoAccessToken = user.kakaoAccessToken,
                refreshToken = user.refreshToken,
                profileImage = user.profileImage,
                role = user.role
            )
        }
    }

    fun toDomain(): User {
        return User(
            id = this.id,
            nickName = this.nickName,
            profileImage = this.profileImage,
            role = this.role,
            kakaoAccessToken = this.kakaoAccessToken,
            email = this.email,
            refreshToken = this.refreshToken
        )
    }
}