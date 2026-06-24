package com.example.cakratreasury.domain.model

import java.util.Date

data class User(
    val id: Long = 0,
    val companyId: Long,
    val fullName: String,
    val email: String,
    val role: UserRole,
    val createdAt: Date = Date()
)

enum class UserRole {
    ADMIN,
    CFO,
    TREASURY_MANAGER,
    ACCOUNTANT,
    VIEWER
}
