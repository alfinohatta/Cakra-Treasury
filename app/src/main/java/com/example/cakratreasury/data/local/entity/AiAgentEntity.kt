package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.AgentStatus
import com.example.cakratreasury.domain.model.AgentType
import java.util.Date

@Entity(tableName = "ai_agents")
data class AiAgentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val agentName: String,
    val agentType: AgentType,
    val status: AgentStatus = AgentStatus.ACTIVE,
    val createdAt: Date = Date()
)
