package com.example.cakratreasury.ui.policy

import com.example.cakratreasury.domain.model.TreasuryPolicy

data class PolicyState(
    val policy: TreasuryPolicy = TreasuryPolicy(),
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null
)
