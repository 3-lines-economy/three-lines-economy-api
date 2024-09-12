package com.example.tleapplication.storage.user

import com.example.tleapplication.domain.user.User
import com.example.tleapplication.storage.common.BaseEntity
import com.example.tleapplication.domain.user.UserRoleEnum
import jakarta.persistence.*

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
                nickName = user.nickName,
                profileImage = user.profileImage,
                role = user.role,
                email = user.email,
                refreshToken = user.refreshToken
            )
        }
    }

    fun toDomain(): User {
        return User(
            id = this.id,
            nickName = this.nickName,
            profileImage = this.profileImage,
            role = this.role,
            email = this.email,
            refreshToken = this.refreshToken
        )
    }
}