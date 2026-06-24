package com.example.cakratreasury.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cakratreasury.domain.repository.TreasuryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: TreasuryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsState())
    val state: StateFlow<TransactionsState> = _state.asStateFlow()

    init {
        loadTransactions(companyId = 1L)
    }

    private fun loadTransactions(companyId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.getTransactions(companyId)
                .catch { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { transactions ->
                    _state.update { it.copy(transactions = transactions, isLoading = false) }
                }
        }
    }
}
