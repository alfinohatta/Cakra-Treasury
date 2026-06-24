package com.example.cakratreasury.data.local

import com.example.cakratreasury.data.local.dao.*
import com.example.cakratreasury.data.local.entity.*
import com.example.cakratreasury.domain.model.*
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSeeder @Inject constructor(
    private val companyDao: CompanyDao,
    private val bankAccountDao: BankAccountDao,
    private val recommendationDao: TreasuryRecommendationDao,
    private val forecastDao: CashFlowForecastDao,
    private val aiAgentDao: AiAgentDao,
    private val fxExposureDao: FxExposureDao,
    private val invoiceDao: InvoiceDao,
    private val supplierPayableDao: SupplierPayableDao,
    private val transactionDao: TransactionDao,
    private val agentThoughtDao: AgentThoughtDao
) {
    suspend fun seedIfNeeded() {
        val companies = companyDao.getCompanyById(1L)
        if (companies == null) {
            // Seed Company
            companyDao.insertCompany(
                CompanyEntity(
                    id = 1,
                    companyName = "Cakra Food Distributor",
                    industry = "Logistics",
                    taxId = "01.234.567.8-901.000",
                    companySize = CompanySize.MEDIUM,
                    createdAt = Date(),
                    updatedAt = Date()
                )
            )

            // Seed Bank Accounts
            bankAccountDao.insertBankAccount(
                BankAccountEntity(
                    id = 1,
                    companyId = 1,
                    bankName = "Bank Mandiri",
                    accountNumber = "1234567890",
                    accountName = "Cakra Food Ops",
                    currentBalance = 50000000000.0,
                    currencyCode = "IDR"
                )
            )
            bankAccountDao.insertBankAccount(
                BankAccountEntity(
                    id = 2,
                    companyId = 1,
                    bankName = "BCA",
                    accountNumber = "0987654321",
                    accountName = "Cakra Food Reserve",
                    currentBalance = 15000000000.0,
                    currencyCode = "IDR"
                )
            )

            // Seed Recommendations
            recommendationDao.insertRecommendation(
                TreasuryRecommendationEntity(
                    id = 1,
                    companyId = 1,
                    type = RecommendationType.MONEY_MARKET,
                    recommendationText = "Investasikan Rp15 miliar ke Reksa Dana Pasar Uang untuk mengoptimalkan idle cash selama 60 hari.",
                    expectedReturn = 5.5,
                    riskScore = 1.2,
                    status = RecommendationStatus.PENDING,
                    createdAt = Date()
                )
            )
            
            // Seed Thoughts for Rec 1
            agentThoughtDao.insertThoughts(listOf(
                AgentThoughtEntity(recommendationId = 1, agentType = AgentType.LIQUIDITY, agentName = "Liquidity Agent", thought = "Surplus kas terdeteksi sebesar Rp20 miliar yang tidak akan terpakai dalam 60 hari ke depan."),
                AgentThoughtEntity(recommendationId = 1, agentType = AgentType.RISK, agentName = "Risk Agent", thought = "Profil risiko pasar uang rendah, sesuai dengan kebijakan treasury perusahaan."),
                AgentThoughtEntity(recommendationId = 1, agentType = AgentType.MARKET, agentName = "Market Agent", thought = "Imbal hasil pasar uang saat ini sedang kompetitif dibandingkan deposito 1 bulan.")
            ))

            recommendationDao.insertRecommendation(
                TreasuryRecommendationEntity(
                    id = 2,
                    companyId = 1,
                    type = RecommendationType.LIQUIDITY_RESERVE,
                    recommendationText = "Sisakan Rp30 miliar sebagai cadangan likuiditas untuk pembayaran supplier bulan depan.",
                    expectedReturn = 0.0,
                    riskScore = 0.0,
                    status = RecommendationStatus.APPROVED,
                    createdAt = Date()
                )
            )
            
            // Seed Thoughts for Rec 2
            agentThoughtDao.insertThoughts(listOf(
                AgentThoughtEntity(recommendationId = 2, agentType = AgentType.COMPLIANCE, agentName = "Compliance Agent", thought = "Memastikan rasio lancar tetap terjaga di atas 1.5x sesuai covenant bank."),
                AgentThoughtEntity(recommendationId = 2, agentType = AgentType.LIQUIDITY, agentName = "Liquidity Agent", thought = "Terdapat kewajiban pembayaran supplier sebesar Rp25 miliar yang jatuh tempo dalam 20 hari.")
            ))

            // Seed Forecasts (7, 30, 90, 180 days as per Executive Summary)
            val calendar = Calendar.getInstance()
            val forecastPeriods = listOf(
                ForecastPeriod.SEVEN_DAYS to 7,
                ForecastPeriod.THIRTY_DAYS to 30,
                ForecastPeriod.NINETY_DAYS to 90,
                ForecastPeriod.ONE_HUNDRED_EIGHTY_DAYS to 180
            )
            
            forecastPeriods.forEachIndexed { index, pair ->
                val forecastCalendar = calendar.clone() as Calendar
                forecastCalendar.add(Calendar.DAY_OF_YEAR, pair.second)
                forecastDao.insertForecast(
                    CashFlowForecastEntity(
                        companyId = 1,
                        forecastDate = forecastCalendar.time,
                        forecastPeriod = pair.first,
                        predictedInflow = 10000000000.0 * (index + 1),
                        predictedOutflow = 8000000000.0 * (index + 1),
                        predictedBalance = 65000000000.0 + (2000000000.0 * (index + 1)),
                        confidenceScore = 0.95 - (index * 0.1),
                        createdAt = Date()
                    )
                )
            }

            // Seed AI Agents
            val agents = listOf(
                Pair("Liquidity Agent", AgentType.LIQUIDITY),
                Pair("Risk Agent", AgentType.RISK),
                Pair("Market Agent", AgentType.MARKET),
                Pair("Compliance Agent", AgentType.COMPLIANCE),
                Pair("Execution Agent", AgentType.EXECUTION)
            )
            agents.forEach { (name, type) ->
                aiAgentDao.insertAgent(
                    AiAgentEntity(
                        agentName = name,
                        agentType = type,
                        status = AgentStatus.ACTIVE,
                        createdAt = Date()
                    )
                )
            }

            // Seed FX Exposures
            fxExposureDao.insertExposure(
                FxExposureEntity(
                    companyId = 1,
                    currencyCode = "USD",
                    exposureAmount = 250000.0,
                    riskLevel = RiskLevel.MEDIUM,
                    createdAt = Date()
                )
            )

            // Seed Invoices (Receivables)
            invoiceDao.insertInvoice(
                InvoiceEntity(
                    companyId = 1,
                    invoiceNumber = "INV-2023-001",
                    customerName = "IndoMarket Retail",
                    invoiceAmount = 1200000000.0,
                    dueDate = Date(System.currentTimeMillis() + 86400000L * 5),
                    status = InvoiceStatus.PENDING
                )
            )

            // Seed Payables
            supplierPayableDao.insertPayable(
                SupplierPayableEntity(
                    companyId = 1,
                    supplierName = "Global Food Supply Co.",
                    payableAmount = 3500000000.0,
                    dueDate = Date(System.currentTimeMillis() + 86400000L * 10),
                    status = PayableStatus.PENDING
                )
            )

            // Seed Transactions
            val txCalendar = Calendar.getInstance()
            val transactions = listOf(
                TransactionEntity(bankAccountId = 1, transactionDate = txCalendar.time, type = TransactionType.INFLOW, category = "Sales", description = "Customer Payment INV-099", amount = 450000000.0),
                TransactionEntity(bankAccountId = 1, transactionDate = txCalendar.apply { add(Calendar.HOUR, -2) }.time, type = TransactionType.OUTFLOW, category = "Payroll", description = "Employee Salaries Oct 2023", amount = 1200000000.0),
                TransactionEntity(bankAccountId = 2, transactionDate = txCalendar.apply { add(Calendar.DAY_OF_YEAR, -1) }.time, type = TransactionType.INFLOW, category = "Interest", description = "Monthly Interest Income", amount = 25000000.0)
            )
            transactions.forEach { transactionDao.insertTransaction(it) }
        }
    }
}
