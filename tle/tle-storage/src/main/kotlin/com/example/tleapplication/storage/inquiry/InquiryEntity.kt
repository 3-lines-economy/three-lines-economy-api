package com.example.tleapplication.storage.inquiry

import com.example.tleapplication.storage.user.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table


@Entity
@Table(
    name = "inquiry",
    indexes = [Index(name = "UK_inquiry_id", columnList = "inquiry_id", unique = true)]
)
class InquiryEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "inquiry_id")
    val id: Long,

    @field:Column(name = "content")
    var content: String,

    @field:Column(name = "files")
    val files: MutableList<String>,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    val user: UserEntity,
) {
}