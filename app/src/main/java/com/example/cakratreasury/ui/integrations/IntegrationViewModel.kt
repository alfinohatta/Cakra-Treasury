package com.example.cakratreasury.ui.integrations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cakratreasury.domain.model.Integration
import com.example.cakratreasury.domain.model.IntegrationStatus
import com.example.cakratreasury.domain.model.IntegrationType
import com.example.cakratreasury.domain.repository.TreasuryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class IntegrationViewModel @Inject constructor(
    private val repository: TreasuryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(IntegrationState())
    val state: StateFlow<IntegrationState> = _state.asStateFlow()

    init {
        loadIntegrations()
    }

    private fun loadIntegrations() {
        // Mocking integration list based on Executive Summary requirements
        val mockIntegrations = listOf(
            Integration("1", "Bank Mandiri API", IntegrationType.BANK, IntegrationStatus.CONNECTED, Date()),
            Integration("2", "BCA Business API", IntegrationType.BANK, IntegrationStatus.CONNECTED, Date()),
            Integration("3", "SAP ERP Connect", IntegrationType.ERP, IntegrationStatus.DISCONNECTED),
            Integration("4", "Jurnal.id Accounting", IntegrationType.ACCOUNTING, IntegrationStatus.CONNECTED, Date()),
            Integration("5", "Mekari Payroll", IntegrationType.PAYROLL, IntegrationStatus.ERROR)
        )
        _state.update { it.copy(integrations = mockIntegrations) }
    }

    fun toggleIntegration(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000) // Simulate API call
            val current = _state.value.integrations
            val updated = current.map {
                if (it.id == id) {
                    val nextStatus = if (it.status == IntegrationStatus.CONNECTED) 
                        IntegrationStatus.DISCONNECTED else IntegrationStatus.CONNECTED
                    it.copy(status = nextStatus, lastSynced = if (nextStatus == IntegrationStatus.CONNECTED) Date() else it.lastSynced)
                } else it
            }
            _state.update { it.copy(integrations = updated, isLoading = false) }
        }
    }
}
