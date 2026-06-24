package com.example.cakratreasury.ui.integrations

import com.example.cakratreasury.domain.model.Integration

data class IntegrationState(
    val integrations: List<Integration> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
