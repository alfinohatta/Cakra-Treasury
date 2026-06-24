package com.example.cakratreasury.domain.model

data class TreasuryAlert(
    val id: String,
    val title: String,
    val description: String,
    val severity: AlertSeverity,
    val type: AlertType
)

enum class AlertSeverity {
    INFO, WARNING, CRITICAL
}

enum class AlertType {
    LIQUIDITY, FX_RISK, OPPORTUNITY, COMPLIANCE
}
