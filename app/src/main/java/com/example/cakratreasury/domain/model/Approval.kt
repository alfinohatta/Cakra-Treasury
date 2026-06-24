package com.example.cakratreasury.domain.model

import java.util.Date

data class Approval(
    val id: Long = 0,
    val recommendationId: Long,
    val approvedBy: Long?,
    val status: ApprovalStatus,
    val note: String?,
    val approvedAt: Date?
)

enum class ApprovalStatus {
    PENDING, APPROVED, REJECTED
}
