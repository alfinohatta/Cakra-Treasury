package com.example.cakratreasury.ui.scenario

data class ScenarioState(
    // Inputs
    val fxDevaluationPercent: Float = 0f,
    val interestRateIncreasePercent: Float = 0f,
    val salesDecreasePercent: Float = 0f,
    
    // Results (Simulated)
    val cashImpact: Double = 0.0,
    val marginImpactPercent: Float = 0f,
    val profitImpact: Double = 0.0,
    val isSimulating: Boolean = false
)
