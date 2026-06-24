package com.example.cakratreasury.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cakratreasury.data.local.converter.DateConverter
import com.example.cakratreasury.data.local.converter.EnumConverters
import com.example.cakratreasury.data.local.dao.*
import com.example.cakratreasury.data.local.entity.*

@Database(
    entities = [
        CompanyEntity::class,
        UserEntity::class,
        BankAccountEntity::class,
        TransactionEntity::class,
        InvoiceEntity::class,
        SupplierPayableEntity::class,
        CashFlowForecastEntity::class,
        FxExposureEntity::class,
        AiAgentEntity::class,
        TreasuryRecommendationEntity::class,
        ApprovalEntity::class,
        ExecutionLogEntity::class,
        AgentThoughtEntity::class,
        AuditLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, EnumConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun companyDao(): CompanyDao
    abstract fun userDao(): UserDao
    abstract fun bankAccountDao(): BankAccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun supplierPayableDao(): SupplierPayableDao
    abstract fun cashFlowForecastDao(): CashFlowForecastDao
    abstract fun fxExposureDao(): FxExposureDao
    abstract fun aiAgentDao(): AiAgentDao
    abstract fun treasuryRecommendationDao(): TreasuryRecommendationDao
    abstract fun approvalDao(): ApprovalDao
    abstract fun executionLogDao(): ExecutionLogDao
    abstract fun agentThoughtDao(): AgentThoughtDao
    abstract fun auditLogDao(): AuditLogDao

    companion object {
        const val DATABASE_NAME = "cakra_treasury_db"
    }
}
