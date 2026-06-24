package com.example.cakratreasury.ui.scenario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScenarioViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ScenarioState())
    val state: StateFlow<ScenarioState> = _state.asStateFlow()

    fun updateFxDevaluation(value: Float) {
        _state.update { it.copy(fxDevaluationPercent = value) }
        runSimulation()
    }

    fun updateInterestRate(value: Float) {
        _state.update { it.copy(interestRateIncreasePercent = value) }
        runSimulation()
    }

    fun updateSalesDecrease(value: Float) {
        _state.update { it.copy(salesDecreasePercent = value) }
        runSimulation()
    }

    private fun runSimulation() {
        viewModelScope.launch {
            _state.update { it.copy(isSimulating = true) }
            
            // Simple mock logic for demonstration
            // In a real app, this would use a forecasting model
            delay(500) 
            
            val currentCash = 65_000_000_000.0
            val fxImpact = currentCash * (_state.value.fxDevaluationPercent / 100.0) * -0.2 // assuming 20% exposure
            val salesImpact = currentCash * (_state.value.salesDecreasePercent / 100.0) * -0.5
            val interestImpact = currentCash * (_state.value.interestRateIncreasePercent / 100.0) * 0.05
            
            val totalImpact = fxImpact + salesImpact + interestImpact
            
            _state.update { 
                it.copy(
                    cashImpact = totalImpact,
                    profitImpact = totalImpact * 0.8,
                    marginImpactPercent = -(_state.value.salesDecreasePercent * 0.3f + _state.value.fxDevaluationPercent * 0.1f),
                    isSimulating = false
                )
            }
        }
    }
}
