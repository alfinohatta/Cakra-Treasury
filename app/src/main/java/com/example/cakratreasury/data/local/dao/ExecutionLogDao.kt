package com.example.cakratreasury.data.local.dao

import androidx.room.*
import com.example.cakratreasury.data.local.entity.ExecutionLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExecutionLogDao {
    @Query("""
        SELECT el.* FROM execution_logs el
        INNER JOIN treasury_recommendations tr ON el.recommendationId = tr.id
        WHERE tr.companyId = :companyId
        ORDER BY el.executedAt DESC
    """)
    fun getExecutionLogsByCompany(companyId: Long): Flow<List<ExecutionLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ExecutionLogEntity): Long
}
