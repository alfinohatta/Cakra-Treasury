package com.example.cakratreasury.di

import android.content.Context
import androidx.room.Room
import com.example.cakratreasury.data.local.AppDatabase
import com.example.cakratreasury.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideCompanyDao(database: AppDatabase): CompanyDao = database.companyDao()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    fun provideBankAccountDao(database: AppDatabase): BankAccountDao = database.bankAccountDao()

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao = database.transactionDao()

    @Provides
    fun provideInvoiceDao(database: AppDatabase): InvoiceDao = database.invoiceDao()

    @Provides
    fun provideSupplierPayableDao(database: AppDatabase): SupplierPayableDao = database.supplierPayableDao()

    @Provides
    fun provideCashFlowForecastDao(database: AppDatabase): CashFlowForecastDao = database.cashFlowForecastDao()

    @Provides
    fun provideFxExposureDao(database: AppDatabase): FxExposureDao = database.fxExposureDao()

    @Provides
    fun provideAiAgentDao(database: AppDatabase): AiAgentDao = database.aiAgentDao()

    @Provides
    fun provideTreasuryRecommendationDao(database: AppDatabase): TreasuryRecommendationDao = database.treasuryRecommendationDao()

    @Provides
    fun provideApprovalDao(database: AppDatabase): ApprovalDao = database.approvalDao()

    @Provides
    fun provideExecutionLogDao(database: AppDatabase): ExecutionLogDao = database.executionLogDao()

    @Provides
    fun provideAgentThoughtDao(database: AppDatabase): AgentThoughtDao = database.agentThoughtDao()

    @Provides
    fun provideAuditLogDao(database: AppDatabase): AuditLogDao = database.auditLogDao()
}
