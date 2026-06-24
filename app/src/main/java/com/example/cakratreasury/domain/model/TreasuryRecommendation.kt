package com.example.cakratreasury.domain.model

import java.util.Date

data class TreasuryRecommendation(
    val id: Long = 0,
    val companyId: Long,
    val type: RecommendationType,
    val recommendationText: String?,
    val expectedReturn: Double?,
    val riskScore: Double?,
    val status: RecommendationStatus = RecommendationStatus.PENDING,
    val reasoning: List<AgentThought> = emptyList(),
    val createdAt: Date = Date()
)

data class AgentThought(
    val agentName: String,
    val thought: String
)

enum class RecommendationType {
    DEPOSIT, MONEY_MARKET, BOND, FX_HEDGE, LIQUIDITY_RESERVE
}

enum class RecommendationStatus {
    PENDING, APPROVED, REJECTED, EXECUTED
}
