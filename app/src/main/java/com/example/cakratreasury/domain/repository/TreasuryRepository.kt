package com.example.cakratreasury.domain.repository

import com.example.cakratreasury.data.local.entity.AuditLogEntity
import com.example.cakratreasury.domain.model.*
import kotlinx.coroutines.flow.Flow

interface TreasuryRepository {
    fun getBankAccounts(companyId: Long): Flow<List<BankAccount>>
    fun getForecasts(companyId: Long): Flow<List<CashFlowForecast>>
    fun getRecommendations(companyId: Long): Flow<List<TreasuryRecommendation>>
    fun getAgents(): Flow<List<AiAgent>>
    fun getFxExposures(companyId: Long): Flow<List<FxExposure>>
    fun getInvoices(companyId: Long): Flow<List<Invoice>>
    fun getPayables(companyId: Long): Flow<List<SupplierPayable>>
    fun getTransactions(companyId: Long): Flow<List<Transaction>>
    fun getExecutionLogs(companyId: Long): Flow<List<ExecutionLog>>
    fun getAuditLogs(): Flow<List<AuditLogEntity>>
    fun getIntegrations(): Flow<List<Integration>>
    fun getAlerts(companyId: Long): Flow<List<TreasuryAlert>>
    
    suspend fun getBankAccountById(id: Long): BankAccount?
    suspend fun updateBankAccountBalance(accountId: Long, newBalance: Double)
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun updateRecommendationStatus(recommendationId: Long, status: String)
    suspend fun insertAuditLog(log: AuditLogEntity)
    suspend fun insertBankAccount(bankAccount: BankAccount)
}
