package com.example.cakratreasury.data.local.dao

import androidx.room.*
import com.example.cakratreasury.data.local.entity.TreasuryRecommendationEntity
import com.example.cakratreasury.data.local.relation.RecommendationWithThoughts
import kotlinx.coroutines.flow.Flow

@Dao
interface TreasuryRecommendationDao {
    @Transaction
    @Query("SELECT * FROM treasury_recommendations WHERE companyId = :companyId ORDER BY createdAt DESC")
    fun getRecommendationsWithThoughts(companyId: Long): Flow<List<RecommendationWithThoughts>>

    @Query("SELECT * FROM treasury_recommendations WHERE id = :id")
    suspend fun getRecommendationById(id: Long): TreasuryRecommendationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendation(recommendation: TreasuryRecommendationEntity): Long

    @Update
    suspend fun updateRecommendation(recommendation: TreasuryRecommendationEntity)
}
