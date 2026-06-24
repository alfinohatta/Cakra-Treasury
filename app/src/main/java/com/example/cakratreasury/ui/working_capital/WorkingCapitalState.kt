package com.example.cakratreasury.ui.working_capital

import com.example.cakratreasury.domain.model.Invoice
import com.example.cakratreasury.domain.model.SupplierPayable

data class WorkingCapitalState(
    val invoices: List<Invoice> = emptyList(),
    val payables: List<SupplierPayable> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
