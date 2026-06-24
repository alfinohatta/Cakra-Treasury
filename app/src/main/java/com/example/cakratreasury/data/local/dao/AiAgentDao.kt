package com.example.cakratreasury.data.local.dao

import androidx.room.*
import com.example.cakratreasury.data.local.entity.AiAgentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiAgentDao {
    @Query("SELECT * FROM ai_agents")
    fun getAllAgents(): Flow<List<AiAgentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAgent(agent: AiAgentEntity): Long

    @Update
    suspend fun updateAgent(agent: AiAgentEntity)
}
