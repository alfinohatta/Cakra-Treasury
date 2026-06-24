package com.example.cakratreasury.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cakratreasury.data.local.entity.AgentThoughtEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentThoughtDao {
    @Query("SELECT * FROM agent_thoughts WHERE recommendationId = :recommendationId")
    fun getThoughtsForRecommendation(recommendationId: Long): Flow<List<AgentThoughtEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThought(thought: AgentThoughtEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThoughts(thoughts: List<AgentThoughtEntity>)
}
