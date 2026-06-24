package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.UserRole
import java.util.Date

@Entity(
    tableName = "users",
    foreignKeys = [
        ForeignKey(
            entity = CompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("companyId")]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val companyId: Long,
    val fullName: String,
    val email: String,
    val passwordHash: String,
    val role: UserRole,
    val createdAt: Date
)
