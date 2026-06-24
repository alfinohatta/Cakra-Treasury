package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.TransactionType
import java.util.Date

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = BankAccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["bankAccountId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("bankAccountId")]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bankAccountId: Long,
    val transactionDate: Date,
    val type: TransactionType,
    val category: String?,
    val description: String?,
    val amount: Double,
    val currencyCode: String = "IDR",
    val createdAt: Date = Date()
)
