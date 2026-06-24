package com.example.cakratreasury.domain.model

data class Integration(
    val id: String,
    val name: String,
    val type: IntegrationType,
    val status: IntegrationStatus,
    val lastSynced: java.util.Date? = null
)

enum class IntegrationType {
    BANK, ERP, ACCOUNTING, PAYROLL
}

enum class IntegrationStatus {
    CONNECTED, DISCONNECTED, ERROR
}
