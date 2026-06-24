package com.example.cakratreasury.domain.model

import java.util.Date

data class Invoice(
    val id: Long = 0,
    val companyId: Long,
    val invoiceNumber: String?,
    val customerName: String?,
    val invoiceAmount: Double,
    val dueDate: Date?,
    val status: InvoiceStatus = InvoiceStatus.PENDING,
    val createdAt: Date = Date()
)

enum class InvoiceStatus {
    PENDING, PAID, OVERDUE
}
