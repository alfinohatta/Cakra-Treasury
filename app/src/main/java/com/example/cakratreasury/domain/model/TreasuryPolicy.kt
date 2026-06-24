package com.example.cakratreasury.domain.model

data class TreasuryPolicy(
    val autonomousApprovalLimit: Double = 100_000_000.0, // AI can auto-approve below this
    val minRiskScoreForManual: Double = 2.0, // Risk above this requires Biometric
    val maxConcentrationPerBankPercent: Float = 30f,
    val allowedInstruments: List<RecommendationType> = listOf(
        RecommendationType.DEPOSIT,
        RecommendationType.MONEY_MARKET,
        RecommendationType.LIQUIDITY_RESERVE
    )
)
