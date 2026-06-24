package com.example.cakratreasury.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cakratreasury.data.local.DataSeeder
import com.example.cakratreasury.data.local.entity.AuditLogEntity
import com.example.cakratreasury.domain.model.*
import com.example.cakratreasury.domain.repository.TreasuryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: TreasuryRepository,
    private val dataSeeder: DataSeeder
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            dataSeeder.seedIfNeeded()
            loadDashboardData(companyId = 1L)
        }
    }

    private fun loadDashboardData(companyId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            combine(
                repository.getBankAccounts(companyId),
                repository.getForecasts(companyId),
                repository.getRecommendations(companyId),
                repository.getAgents(),
                repository.getFxExposures(companyId),
                repository.getInvoices(companyId),
                repository.getPayables(companyId),
                repository.getExecutionLogs(companyId),
                repository.getAlerts(companyId)
            ) { flowResults ->
                val accounts = flowResults[0] as List<BankAccount>
                val forecasts = flowResults[1] as List<CashFlowForecast>
                val recommendations = flowResults[2] as List<TreasuryRecommendation>
                val agents = flowResults[3] as List<AiAgent>
                val fxExposures = flowResults[4] as List<FxExposure>
                val invoices = flowResults[5] as List<Invoice>
                val payables = flowResults[6] as List<SupplierPayable>
                val executionLogs = flowResults[7] as List<ExecutionLog>
                val alerts = flowResults[8] as List<TreasuryAlert>

                val totalBalance = accounts.sumOf { it.currentBalance }
                
                // Deep FX Metrics Calculation (Mocked for demo based on exposure)
                val totalFxExposure = fxExposures.sumOf { it.exposureAmount }
                val currencyAtRisk = totalFxExposure * 0.15 // Assume 15% volatility risk
                val hedgingRatio = if (totalFxExposure > 0) 0.65f else 0.0f // Mock 65% hedged

                // Auto-Approval logic for low risk in Full Autonomous mode
                if (_state.value.operationMode == OperationMode.FULL_AUTONOMOUS) {
                    recommendations.filter { it.status == RecommendationStatus.PENDING && (it.riskScore ?: 5.0) < 1.5 }
                        .forEach { autoRec ->
                            approveRecommendation(autoRec.id)
                        }
                }

                DashboardState(
                    bankAccounts = accounts,
                    forecasts = forecasts,
                    recommendations = recommendations,
                    agents = agents,
                    fxExposures = fxExposures,
                    invoices = invoices,
                    payables = payables,
                    executionLogs = executionLogs,
                    alerts = alerts,
                    totalBalance = totalBalance,
                    dso = 42,
                    dpo = 35,
                    ccc = 22,
                    treasuryYield = 250_000_000.0,
                    utilizationRate = 0.85f,
                    cashBurnRate = totalBalance / 180,
                    currencyAtRisk = currencyAtRisk,
                    hedgingRatio = hedgingRatio,
                    operationMode = _state.value.operationMode,
                    isLoading = false,
                    isSyncing = _state.value.isSyncing,
                    isExporting = _state.value.isExporting,
                    aiPrompt = _state.value.aiPrompt
                )
            }.catch { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }.collect { newState ->
                _state.value = newState
            }
        }
    }

    fun onPromptChanged(newPrompt: String) {
        _state.update { it.copy(aiPrompt = newPrompt) }
    }

    fun sendAiCommand() {
        val command = _state.value.aiPrompt
        if (command.isBlank()) return
        
        viewModelScope.launch {
            _state.update { it.copy(isSyncing = true) }
            delay(1500) 
            _state.update { 
                it.copy(
                    isSyncing = false, 
                    aiPrompt = "",
                    error = "AI Command Executed: Analisis treasury selesai. Skenario baru telah disiapkan."
                )
            }
            delay(4000)
            _state.update { it.copy(error = null) }
        }
    }

    fun syncData() {
        viewModelScope.launch {
            _state.update { it.copy(isSyncing = true) }
            delay(2000)
            _state.update { it.copy(isSyncing = false) }
        }
    }

    fun approveRecommendation(id: Long) {
        viewModelScope.launch {
            repository.updateRecommendationStatus(id, RecommendationStatus.APPROVED.name)
            runAutonomousExecution(id)
        }
    }

    private fun runAutonomousExecution(recId: Long) {
        viewModelScope.launch {
            val steps = listOf(
                "Authenticating with Bank API gateways...",
                "Executing fund transfer to Money Market...",
                "Confirming placement with instrument provider...",
                "Finalizing audit trail and reporting..."
            )
            
            steps.forEach { step ->
                repository.insertAuditLog(AuditLogEntity(
                    userId = 0, // 0 for AI Agent
                    action = "EXECUTION_STEP",
                    entityName = "EXECUTION_AGENT",
                    entityId = recId,
                    oldValue = null,
                    newValue = step
                ))
                delay(1500)
            }
            
            repository.updateRecommendationStatus(recId, RecommendationStatus.EXECUTED.name)
        }
    }

    fun exportReport() {
        viewModelScope.launch {
            _state.update { it.copy(isExporting = true) }
            delay(3000)
            _state.update { 
                it.copy(
                    isExporting = false,
                    error = "Report exported: Documents/Treasury_Report_Oct23.pdf"
                )
            }
            delay(5000)
            _state.update { it.copy(error = null) }
        }
    }

    fun changeOperationMode(mode: OperationMode) {
        _state.update { it.copy(operationMode = mode) }
    }

    fun rejectRecommendation(id: Long) {
        viewModelScope.launch {
            repository.updateRecommendationStatus(id, RecommendationStatus.REJECTED.name)
        }
    }
}
