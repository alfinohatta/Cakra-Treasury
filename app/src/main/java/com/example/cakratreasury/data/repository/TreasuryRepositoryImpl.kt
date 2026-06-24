package com.example.cakratreasury.data.repository

import com.example.cakratreasury.data.local.dao.*
import com.example.cakratreasury.data.local.entity.AuditLogEntity
import com.example.cakratreasury.data.mapper.toDomain
import com.example.cakratreasury.data.mapper.toEntity
import com.example.cakratreasury.domain.model.*
import com.example.cakratreasury.domain.repository.TreasuryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TreasuryRepositoryImpl @Inject constructor(
    private val bankAccountDao: BankAccountDao,
    private val forecastDao: CashFlowForecastDao,
    private val recommendationDao: TreasuryRecommendationDao,
    private val aiAgentDao: AiAgentDao,
    private val fxExposureDao: FxExposureDao,
    private val invoiceDao: InvoiceDao,
    private val supplierPayableDao: SupplierPayableDao,
    private val transactionDao: TransactionDao,
    private val agentThoughtDao: AgentThoughtDao,
    private val executionLogDao: ExecutionLogDao,
    private val auditLogDao: AuditLogDao
) : TreasuryRepository {

    override fun getBankAccounts(companyId: Long): Flow<List<BankAccount>> {
        return bankAccountDao.getBankAccountsByCompany(companyId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getForecasts(companyId: Long): Flow<List<CashFlowForecast>> {
        return forecastDao.getForecastsByCompany(companyId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getRecommendations(companyId: Long): Flow<List<TreasuryRecommendation>> {
        return recommendationDao.getRecommendationsWithThoughts(companyId).map { relations ->
            relations.map { relation ->
                relation.recommendation.toDomain(relation.thoughts.map { it.toDomain() })
            }
        }
    }

    override fun getAgents(): Flow<List<AiAgent>> {
        return aiAgentDao.getAllAgents().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getFxExposures(companyId: Long): Flow<List<FxExposure>> {
        return fxExposureDao.getExposuresByCompany(companyId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getInvoices(companyId: Long): Flow<List<Invoice>> {
        return invoiceDao.getInvoicesByCompany(companyId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getPayables(companyId: Long): Flow<List<SupplierPayable>> {
        return supplierPayableDao.getPayablesByCompany(companyId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransactions(companyId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByCompany(companyId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getExecutionLogs(companyId: Long): Flow<List<ExecutionLog>> {
        return executionLogDao.getExecutionLogsByCompany(companyId).map { entities ->
            entities.map { it.toDomain("Autonomous Action") }
        }
    }

    override fun getAuditLogs(): Flow<List<AuditLogEntity>> {
        return auditLogDao.getAllLogs()
    }

    override fun getIntegrations(): Flow<List<Integration>> {
        return flow { emit(emptyList()) }
    }

    override fun getAlerts(companyId: Long): Flow<List<TreasuryAlert>> {
        return flow { emit(emptyList()) }
    }

    override suspend fun getBankAccountById(id: Long): BankAccount? {
        return bankAccountDao.getBankAccountById(id)?.toDomain()
    }

    override suspend fun updateBankAccountBalance(accountId: Long, newBalance: Double) {
        val account = bankAccountDao.getBankAccountById(accountId)
        account?.let {
            val updated = it.copy(currentBalance = newBalance)
            bankAccountDao.updateBankAccount(updated)
        }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun updateRecommendationStatus(recommendationId: Long, status: String) {
        val recommendation = recommendationDao.getRecommendationById(recommendationId)
        recommendation?.let {
            val updated = it.copy(status = RecommendationStatus.valueOf(status))
            recommendationDao.updateRecommendation(updated)
        }
    }

    override suspend fun insertAuditLog(log: AuditLogEntity) {
        auditLogDao.insertLog(log)
    }

    override suspend fun insertBankAccount(bankAccount: BankAccount) {
        bankAccountDao.insertBankAccount(bankAccount.toEntity())
    }
}
