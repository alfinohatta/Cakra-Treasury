package com.example.cakratreasury.data.local.dao

import androidx.room.*
import com.example.cakratreasury.data.local.entity.SupplierPayableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierPayableDao {
    @Query("SELECT * FROM supplier_payables WHERE companyId = :companyId ORDER BY dueDate ASC")
    fun getPayablesByCompany(companyId: Long): Flow<List<SupplierPayableEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayable(payable: SupplierPayableEntity): Long

    @Update
    suspend fun updatePayable(payable: SupplierPayableEntity)

    @Delete
    suspend fun deletePayable(payable: SupplierPayableEntity)
}
