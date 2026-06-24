package com.example.cakratreasury.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cakratreasury.domain.model.ForecastPeriod
import java.util.Date

@Entity(
    tableName = "cash_flow_forecasts",
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
data class CashFlowForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val companyId: Long,
    val forecastDate: Date,
    val forecastPeriod: ForecastPeriod,
    val predictedInflow: Double,
    val predictedOutflow: Double,
    val predictedBalance: Double,
    val confidenceScore: Double,
    val createdAt: Date = Date()
)
