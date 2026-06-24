package com.example.cakratreasury.ui.audit

import com.example.cakratreasury.data.local.entity.AuditLogEntity

data class AuditLogState(
    val logs: List<AuditLogEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
