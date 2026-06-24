package com.example.cakratreasury.ui.integrations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LinkOff
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
import com.example.cakratreasury.domain.model.Integration
import com.example.cakratreasury.domain.model.IntegrationStatus
import com.example.cakratreasury.ui.dashboard.formatDate
import com.example.cakratreasury.ui.theme.ErrorRed
import com.example.cakratreasury.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntegrationScreen(viewModel: IntegrationViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ERP & Bank Connectivity", fontWeight = FontWeight.Bold) },
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
                    Text(
                        "Manage your connections to external ERP, Accounting, and Banking systems.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                items(state.integrations) { integration ->
                    IntegrationItem(
                        integration = integration,
                        onToggle = { viewModel.toggleIntegration(integration.id) }
                    )
                }
                
                item {
                    Button(
                        onClick = { /* Navigate to Add Integration */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Add New System Connection")
                    }
                }
            }
        }
    }
}

@Composable
fun IntegrationItem(integration: Integration, onToggle: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = when(integration.status) {
                        IntegrationStatus.CONNECTED -> Icons.Default.CheckCircle
                        IntegrationStatus.DISCONNECTED -> Icons.Default.LinkOff
                        IntegrationStatus.ERROR -> Icons.Default.Error
                    },
                    contentDescription = null,
                    tint = when(integration.status) {
                        IntegrationStatus.CONNECTED -> SuccessGreen
                        IntegrationStatus.DISCONNECTED -> Color.Gray
                        IntegrationStatus.ERROR -> ErrorRed
                    },
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(integration.name, fontWeight = FontWeight.Bold)
                    Text(integration.type.name, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    integration.lastSynced?.let {
                        Text("Last sync: ${formatDate(it)}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Switch(
                checked = integration.status == IntegrationStatus.CONNECTED,
                onCheckedChange = { onToggle() }
            )
        }
    }
}
