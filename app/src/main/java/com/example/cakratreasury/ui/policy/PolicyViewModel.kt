package com.example.cakratreasury.ui.policy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cakratreasury.domain.model.RecommendationType
import com.example.cakratreasury.domain.model.TreasuryPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(PolicyState())
    val state: StateFlow<PolicyState> = _state.asStateFlow()

    init {
        loadPolicy()
    }

    private fun loadPolicy() {
        // Mock loading from a data store or DB
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(500)
            _state.update { it.copy(policy = TreasuryPolicy(), isLoading = false) }
        }
    }

    fun updateThreshold(limit: Double) {
        _state.update { it.copy(policy = it.policy.copy(autonomousApprovalLimit = limit)) }
    }

    fun updateRiskScore(score: Double) {
        _state.update { it.copy(policy = it.policy.copy(minRiskScoreForManual = score)) }
    }

    fun toggleInstrument(type: RecommendationType) {
        val current = _state.value.policy.allowedInstruments.toMutableList()
        if (current.contains(type)) {
            current.remove(type)
        } else {
            current.add(type)
        }
        _state.update { it.copy(policy = it.policy.copy(allowedInstruments = current)) }
    }

    fun savePolicy() {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            delay(1500)
            _state.update { it.copy(isSaving = false) }
        }
    }
}
