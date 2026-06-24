package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.InvoiceStatus
import java.util.Date

@Entity(
    tableName = "invoices",
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
data class InvoiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val companyId: Long,
    val invoiceNumber: String?,
    val customerName: String?,
    val invoiceAmount: Double,
    val dueDate: Date?,
    val status: InvoiceStatus = InvoiceStatus.PENDING,
    val createdAt: Date = Date()
)
