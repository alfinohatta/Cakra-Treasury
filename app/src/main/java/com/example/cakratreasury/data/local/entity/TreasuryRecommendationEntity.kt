package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.RecommendationStatus
import com.example.cakratreasury.domain.model.RecommendationType
import java.util.Date

@Entity(
    tableName = "treasury_recommendations",
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
data class TreasuryRecommendationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val companyId: Long,
    val type: RecommendationType,
    val recommendationText: String?,
    val expectedReturn: Double?,
    val riskScore: Double?,
    val status: RecommendationStatus = RecommendationStatus.PENDING,
    val createdAt: Date = Date()
)
