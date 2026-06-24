package com.example.cakratreasury.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cakratreasury.data.local.entity.AgentThoughtEntity
import com.example.cakratreasury.data.local.entity.TreasuryRecommendationEntity

data class RecommendationWithThoughts(
    @Embedded val recommendation: TreasuryRecommendationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recommendationId"
    )
    val thoughts: List<AgentThoughtEntity>
)
