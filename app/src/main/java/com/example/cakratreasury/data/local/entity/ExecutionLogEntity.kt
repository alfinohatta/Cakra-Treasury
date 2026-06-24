package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.ExecutionStatus
import java.util.Date

@Entity(
    tableName = "execution_logs",
    foreignKeys = [
        ForeignKey(
            entity = TreasuryRecommendationEntity::class,
            parentColumns = ["id"],
            childColumns = ["recommendationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("recommendationId")]
)
data class ExecutionLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recommendationId: Long,
    val status: ExecutionStatus,
    val message: String?,
    val executedAt: Date?
)
