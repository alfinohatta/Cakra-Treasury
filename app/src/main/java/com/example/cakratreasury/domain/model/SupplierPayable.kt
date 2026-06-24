package com.example.cakratreasury.domain.model

import java.util.Date

data class SupplierPayable(
    val id: Long = 0,
    val companyId: Long,
    val supplierName: String?,
    val payableAmount: Double,
    val dueDate: Date?,
    val status: PayableStatus = PayableStatus.PENDING,
    val createdAt: Date = Date()
)

enum class PayableStatus {
    PENDING, PAID, OVERDUE
}
