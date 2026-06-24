package com.example.cakratreasury.domain.model

import java.util.Date

data class CashFlowForecast(
    val id: Long = 0,
    val companyId: Long,
    val forecastDate: Date,
    val forecastPeriod: ForecastPeriod,
    val predictedInflow: Double,
    val predictedOutflow: Double,
    val predictedBalance: Double,
    val confidenceScore: Double,
    val createdAt: Date = Date()
)

enum class ForecastPeriod {
    SEVEN_DAYS,
    THIRTY_DAYS,
    NINETY_DAYS,
    ONE_HUNDRED_EIGHTY_DAYS
}
