package com.example.cakratreasury.ui.dashboard

import com.example.cakratreasury.domain.model.*

data class DashboardState(
    val bankAccounts: List<BankAccount> = emptyList(),
    val forecasts: List<CashFlowForecast> = emptyList(),
    val recommendations: List<TreasuryRecommendation> = emptyList(),
    val agents: List<AiAgent> = emptyList(),
    val fxExposures: List<FxExposure> = emptyList(),
    val invoices: List<Invoice> = emptyList(),
    val payables: List<SupplierPayable> = emptyList(),
    val executionLogs: List<ExecutionLog> = emptyList(),
    val alerts: List<TreasuryAlert> = emptyList(),
    val operationMode: OperationMode = OperationMode.ADVISORY,
    
    // Treasury Metrics
    val dso: Int = 0,
    val dpo: Int = 0,
    val ccc: Int = 0,
    val treasuryYield: Double = 0.0,
    val utilizationRate: Float = 0.0f,
    val cashBurnRate: Double = 0.0,
    
    // Deep FX Metrics
    val currencyAtRisk: Double = 0.0,
    val hedgingRatio: Float = 0.0f,
    
    val totalBalance: Double = 0.0,
    val isLoading: Boolean = false,
    val isSyncing: Boolean = false,
    val isExporting: Boolean = false,
    val aiPrompt: String = "",
    val error: String? = null
)
