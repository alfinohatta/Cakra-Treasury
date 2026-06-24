package com.example.cakratreasury.domain.model

import java.util.Date

data class Company(
    val id: Long = 0,
    val companyName: String,
    val industry: String?,
    val taxId: String?,
    val companySize: CompanySize,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class CompanySize {
    SMALL, MEDIUM, LARGE, ENTERPRISE
}
