package com.example.cakratreasury.ui.policy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
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
import com.example.cakratreasury.domain.model.RecommendationType
import com.example.cakratreasury.ui.dashboard.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolicyScreen(viewModel: PolicyViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Treasury Policy Configuration", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { viewModel.savePolicy() }, enabled = !state.isSaving) {
                        if (state.isSaving) CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        else Icon(Icons.Default.Save, contentDescription = "Save Policy")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
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
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Text(
                        "Set the constraints for AI Autonomous actions. These rules act as the 'Financial Guardrails' for the system.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                item {
                    SectionHeader("Autonomous Threshold")
                    Text(
                        "Maximum amount for automatic approval without manual human intervention.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = state.policy.autonomousApprovalLimit.toFloat(),
                        onValueChange = { viewModel.updateThreshold(it.toDouble()) },
                        valueRange = 0f..1_000_000_000f,
                        steps = 10
                    )
                    Text(
                        text = "Limit: Rp ${String.format("%,.0f", state.policy.autonomousApprovalLimit)}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                item {
                    SectionHeader("Risk Tolerance")
                    Text(
                        "AI will flag any recommendation with a Risk Score higher than this for mandatory Biometric approval.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = state.policy.minRiskScoreForManual.toFloat(),
                        onValueChange = { viewModel.updateRiskScore(it.toDouble()) },
                        valueRange = 0f..5f
                    )
                    Text(
                        text = "Threshold Score: ${String.format("%.1f", state.policy.minRiskScoreForManual)} / 5.0",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                item {
                    SectionHeader("Allowed Investment Instruments")
                }

                items(RecommendationType.values()) { type ->
                    val isAllowed = state.policy.allowedInstruments.contains(type)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(type.name.replace("_", " "))
                        Switch(
                            checked = isAllowed,
                            onCheckedChange = { viewModel.toggleInstrument(type) }
                        )
                    }
                }
            }
        }
    }
}
