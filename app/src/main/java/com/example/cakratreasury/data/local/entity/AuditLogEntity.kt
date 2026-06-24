package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long?,
    val action: String,
    val entityName: String,
    val entityId: Long,
    val oldValue: String?,
    val newValue: String?,
    val createdAt: Date = Date()
)
