package com.example.cakratreasury.domain.model

import java.util.Date

data class ExecutionLog(
    val id: Long = 0,
    val recommendationId: Long,
    val recommendationType: String,
    val status: ExecutionStatus,
    val message: String?,
    val executedAt: Date?
)

enum class ExecutionStatus {
    QUEUED, RUNNING, SUCCESS, FAILED
}
