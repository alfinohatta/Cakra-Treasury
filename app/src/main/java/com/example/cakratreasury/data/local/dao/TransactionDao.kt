package com.example.cakratreasury.data.local.dao

import androidx.room.*
import com.example.cakratreasury.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE bankAccountId = :bankAccountId ORDER BY transactionDate DESC")
    fun getTransactionsByAccount(bankAccountId: Long): Flow<List<TransactionEntity>>

    @Query("""
        SELECT t.* FROM transactions t
        INNER JOIN bank_accounts b ON t.bankAccountId = b.id
        WHERE b.companyId = :companyId
        ORDER BY t.transactionDate DESC
    """)
    fun getTransactionsByCompany(companyId: Long): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Long)
}
