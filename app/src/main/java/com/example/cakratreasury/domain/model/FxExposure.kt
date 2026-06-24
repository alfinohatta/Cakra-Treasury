package com.example.cakratreasury.domain.model

import java.util.Date

data class FxExposure(
    val id: Long = 0,
    val companyId: Long,
    val currencyCode: String,
    val exposureAmount: Double,
    val riskLevel: RiskLevel,
    val createdAt: Date = Date()
)

enum class RiskLevel {
    LOW, MEDIUM, HIGH
}
