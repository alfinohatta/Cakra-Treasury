package com.example.cakratreasury.domain.model

import java.util.Date

data class AuditLog(
    val id: Long = 0,
    val userId: Long?,
    val action: String,
    val entityName: String,
    val entityId: Long,
    val oldValue: String?, // Representing JSON as String for now
    val newValue: String?, // Representing JSON as String for now
    val createdAt: Date = Date()
)
