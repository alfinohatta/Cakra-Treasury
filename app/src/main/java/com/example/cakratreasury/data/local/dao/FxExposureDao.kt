package com.example.cakratreasury.data.local.dao

import androidx.room.*
import com.example.cakratreasury.data.local.entity.FxExposureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FxExposureDao {
    @Query("SELECT * FROM fx_exposures WHERE companyId = :companyId")
    fun getExposuresByCompany(companyId: Long): Flow<List<FxExposureEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExposure(exposure: FxExposureEntity): Long

    @Delete
    suspend fun deleteExposure(exposure: FxExposureEntity)
}
