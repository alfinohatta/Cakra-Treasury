package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.ApprovalStatus
import java.util.Date

@Entity(
    tableName = "approvals",
    foreignKeys = [
        ForeignKey(
            entity = TreasuryRecommendationEntity::class,
            parentColumns = ["id"],
            childColumns = ["recommendationId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["approvedBy"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("recommendationId"), Index("approvedBy")]
)
data class ApprovalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recommendationId: Long,
    val approvedBy: Long?,
    val status: ApprovalStatus,
    val note: String?,
    val approvedAt: Date?
)
