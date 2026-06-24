package com.example.cakratreasury.domain.model

import java.util.Date

data class AiAgent(
    val id: Long = 0,
    val agentName: String,
    val agentType: AgentType,
    val status: AgentStatus = AgentStatus.ACTIVE,
    val createdAt: Date = Date()
)

enum class AgentType {
    LIQUIDITY, RISK, MARKET, COMPLIANCE, EXECUTION
}

enum class AgentStatus {
    ACTIVE, INACTIVE
}
