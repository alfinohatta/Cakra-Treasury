package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.CompanySize
import java.util.Date

@Entity(tableName = "companies")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val companyName: String,
    val industry: String?,
    val taxId: String?,
    val companySize: CompanySize,
    val createdAt: Date,
    val updatedAt: Date
)
