package com.example.cakratreasury.ui.scenario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.cakratreasury.ui.dashboard.formatCurrency
import com.example.cakratreasury.ui.theme.ErrorRed
import com.example.cakratreasury.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScenarioScreen(viewModel: ScenarioViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Scenario Analysis", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    "Simulate economic impacts on your treasury",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Input Sliders
            item {
                ScenarioSlider(
                    label = "FX Devaluation (IDR/USD)",
                    value = state.fxDevaluationPercent,
                    onValueChange = { viewModel.updateFxDevaluation(it) },
                    valueRange = 0f..30f,
                    unit = "%"
                )
            }

            item {
                ScenarioSlider(
                    label = "Interest Rate Increase",
                    value = state.interestRateIncreasePercent,
                    onValueChange = { viewModel.updateInterestRate(it) },
                    valueRange = 0f..10f,
                    unit = "%"
                )
            }

            item {
                ScenarioSlider(
                    label = "Sales Decrease Projection",
                    value = state.salesDecreasePercent,
                    onValueChange = { viewModel.updateSalesDecrease(it) },
                    valueRange = 0f..50f,
                    unit = "%"
                )
            }

            // Results Section
            item {
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Simulated Impact", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            item {
                ImpactCard(
                    title = "Estimated Cash Impact",
                    amount = state.cashImpact,
                    isNegative = state.cashImpact < 0
                )
            }

            item {
                ImpactCard(
                    title = "Estimated Profit Impact",
                    amount = state.profitImpact,
                    isNegative = state.profitImpact < 0
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.marginImpactPercent < 0) ErrorRed.copy(alpha = 0.1f) else SuccessGreen.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Margin Impact", fontWeight = FontWeight.Medium)
                        Text(
                            text = "${String.format("%.2f", state.marginImpactPercent)}%",
                            fontWeight = FontWeight.Bold,
                            color = if (state.marginImpactPercent < 0) ErrorRed else SuccessGreen
                        )
                    }
                }
            }
            
            if (state.isSimulating) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ScenarioSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    unit: String
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontWeight = FontWeight.Medium)
            Text("${value.toInt()}$unit", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange
        )
    }
}

@Composable
fun ImpactCard(title: String, amount: Double, isNegative: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Text(
                text = (if (isNegative) "" else "+") + formatCurrency(amount),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = if (isNegative) ErrorRed else SuccessGreen
            )
        }
    }
}
