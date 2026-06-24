package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.BankAccountStatus

@Entity(
    tableName = "bank_accounts",
    foreignKeys = [
        ForeignKey(
            entity = CompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("companyId")]
)
data class BankAccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val companyId: Long,
    val bankName: String?,
    val accountNumber: String?,
    val accountName: String?,
    val currencyCode: String = "IDR",
    val currentBalance: Double = 0.0,
    val status: BankAccountStatus = BankAccountStatus.ACTIVE
)
