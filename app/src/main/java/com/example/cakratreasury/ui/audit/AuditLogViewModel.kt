package com.example.cakratreasury.ui.audit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cakratreasury.domain.repository.TreasuryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuditLogViewModel @Inject constructor(
    private val repository: TreasuryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuditLogState())
    val state: StateFlow<AuditLogState> = _state.asStateFlow()

    init {
        loadLogs()
    }

    private fun loadLogs() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.getAuditLogs()
                .catch { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
                .collect { logs ->
                    _state.update { it.copy(logs = logs, isLoading = false) }
                }
        }
    }
}
