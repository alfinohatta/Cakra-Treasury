package com.example.cakratreasury.ui.transactions

import com.example.cakratreasury.domain.model.Transaction

data class TransactionsState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
