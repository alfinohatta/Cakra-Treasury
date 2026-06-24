package com.example.cakratreasury.domain.model

import java.util.Date

data class Transaction(
    val id: Long = 0,
    val bankAccountId: Long,
    val transactionDate: Date,
    val type: TransactionType,
    val category: String?,
    val description: String?,
    val amount: Double,
    val currencyCode: String = "IDR",
    val createdAt: Date = Date()
)

enum class TransactionType {
    INFLOW, OUTFLOW
}
