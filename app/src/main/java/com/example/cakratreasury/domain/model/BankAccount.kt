package com.example.cakratreasury.domain.model

data class BankAccount(
    val id: Long = 0,
    val companyId: Long,
    val bankName: String?,
    val accountNumber: String?,
    val accountName: String?,
    val currencyCode: String = "IDR",
    val currentBalance: Double = 0.0,
    val status: BankAccountStatus = BankAccountStatus.ACTIVE
)

enum class BankAccountStatus {
    ACTIVE, INACTIVE
}
