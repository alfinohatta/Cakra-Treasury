package com.example.cakratreasury.ui.working_capital

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cakratreasury.domain.model.Invoice
import com.example.cakratreasury.domain.model.SupplierPayable
import com.example.cakratreasury.ui.dashboard.SectionHeader
import com.example.cakratreasury.ui.dashboard.formatCurrency
import com.example.cakratreasury.ui.dashboard.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkingCapitalScreen(viewModel: WorkingCapitalViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Working Capital", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SectionHeader("Accounts Receivable (Invoices)")
                }
                
                if (state.invoices.isEmpty()) {
                    item { Text("No pending invoices.", style = MaterialTheme.typography.bodySmall) }
                } else {
                    items(state.invoices) { invoice ->
                        InvoiceItem(invoice)
                    }
                }

                item {
                    SectionHeader("Accounts Payable (Suppliers)")
                }

                if (state.payables.isEmpty()) {
                    item { Text("No pending payables.", style = MaterialTheme.typography.bodySmall) }
                } else {
                    items(state.payables) { payable ->
                        PayableItem(payable)
                    }
                }
            }
        }
    }
}

@Composable
fun InvoiceItem(invoice: Invoice) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(invoice.customerName ?: "Unknown Customer", fontWeight = FontWeight.Bold)
                Text(invoice.invoiceNumber ?: "No ID", style = MaterialTheme.typography.labelSmall)
                invoice.dueDate?.let {
                    Text("Due: ${formatDate(it)}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
            Text(
                formatCurrency(invoice.invoiceAmount),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )
        }
    }
}

@Composable
fun PayableItem(payable: SupplierPayable) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(payable.supplierName ?: "Unknown Supplier", fontWeight = FontWeight.Bold)
                payable.dueDate?.let {
                    Text("Due: ${formatDate(it)}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
            Text(
                formatCurrency(payable.payableAmount),
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD32F2F)
            )
        }
    }
}
