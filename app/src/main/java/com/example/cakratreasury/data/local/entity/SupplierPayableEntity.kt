package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.PayableStatus
import java.util.Date

@Entity(
    tableName = "supplier_payables",
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
data class SupplierPayableEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val companyId: Long,
    val supplierName: String?,
    val payableAmount: Double,
    val dueDate: Date?,
    val status: PayableStatus = PayableStatus.PENDING,
    val createdAt: Date = Date()
)
