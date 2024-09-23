package com.example.tleapplication.storage.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseEntity(
    @field:Column(name = "created_at", updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime? = null,

    @field:Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,

    @field:Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
) {
}