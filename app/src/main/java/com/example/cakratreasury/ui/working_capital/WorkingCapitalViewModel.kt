package com.example.cakratreasury.ui.working_capital

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cakratreasury.domain.repository.TreasuryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkingCapitalViewModel @Inject constructor(
    private val repository: TreasuryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WorkingCapitalState())
    val state: StateFlow<WorkingCapitalState> = _state.asStateFlow()

    init {
        loadWorkingCapitalData(companyId = 1L)
    }

    private fun loadWorkingCapitalData(companyId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            combine(
                repository.getInvoices(companyId),
                repository.getPayables(companyId)
            ) { invoices, payables ->
                WorkingCapitalState(
                    invoices = invoices,
                    payables = payables,
                    isLoading = false
                )
            }.catch { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }.collect { newState ->
                _state.value = newState
            }
        }
    }
}
