package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.AgentType

@Entity(
    tableName = "agent_thoughts",
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
data class AgentThoughtEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recommendationId: Long,
    val agentType: AgentType,
    val agentName: String,
    val thought: String
)
