package com.example.cakratreasury.data.local.dao

import androidx.room.*
import com.example.cakratreasury.data.local.entity.CashFlowForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CashFlowForecastDao {
    @Query("SELECT * FROM cash_flow_forecasts WHERE companyId = :companyId ORDER BY forecastDate ASC")
    fun getForecastsByCompany(companyId: Long): Flow<List<CashFlowForecastEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: CashFlowForecastEntity): Long

    @Query("DELETE FROM cash_flow_forecasts WHERE companyId = :companyId")
    suspend fun deleteForecastsByCompany(companyId: Long)
}
