package com.example.cakratreasury.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cakratreasury.domain.model.*
import com.example.cakratreasury.ui.theme.ErrorRed
import com.example.cakratreasury.ui.theme.SuccessGreen
import com.example.cakratreasury.ui.theme.WarningOrange
import com.example.cakratreasury.util.BiometricHelper
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    user: User?,
    onLogout: () -> Unit,
    viewModel: DashboardViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    var showModeMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Cakra Treasury", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Mode: ${state.operationMode.name.replace("_", " ")} | ${user?.fullName ?: ""}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.exportReport() }, enabled = !state.isExporting) {
                        if (state.isExporting) CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                        else Icon(Icons.Default.FileDownload, contentDescription = "Export Report")
                    }
                    IconButton(onClick = { viewModel.syncData() }, enabled = !state.isSyncing) {
                        Icon(Icons.Default.Sync, contentDescription = "Sync Data")
                    }
                    Box {
                        IconButton(onClick = { showModeMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Settings")
                        }
                        DropdownMenu(expanded = showModeMenu, onDismissRequest = { showModeMenu = false }) {
                            DropdownMenuItem(
                                text = { Text("Logout") },
                                leadingIcon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                                onClick = {
                                    showModeMenu = false
                                    onLogout()
                                }
                            )
                            HorizontalDivider()
                            OperationMode.values().forEach { mode ->
                                DropdownMenuItem(
                                    text = { Text(mode.name.replace("_", " ")) },
                                    onClick = {
                                        viewModel.changeOperationMode(mode)
                                        showModeMenu = false
                                    }
                                )
                            }
                        }
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
        Column(modifier = Modifier.padding(padding)) {
            AnimatedVisibility(visible = state.isSyncing || state.isExporting) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // AI COMMAND CENTER (Natural Language Interface)
                    item {
                        AiCommandCenter(
                            prompt = state.aiPrompt,
                            lastResponse = state.error, // Reusing error state for simulation messages
                            onPromptChange = viewModel::onPromptChanged,
                            onSend = viewModel::sendAiCommand
                        )
                    }

                    if (state.alerts.isNotEmpty()) {
                        item {
                            SectionHeader("Intelligence Alerts")
                            state.alerts.forEach { alert ->
                                AlertItem(alert)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    item {
                        TotalBalanceCard(state.totalBalance, state.treasuryYield)
                    }

                    item {
                        SectionHeader("Liquidity Mapping")
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Box(modifier = Modifier.weight(1f)) {
                                LiquidityDistributionPieChart(state.bankAccounts)
                            }
                            Box(modifier = Modifier.weight(1.5f)) {
                                LiquidityTrendChart(state.forecasts)
                            }
                        }
                    }

                    item {
                        SectionHeader("Treasury Health Metrics")
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            KpiCard("DSO", "${state.dso} Days", "Receivables", Modifier.weight(1f))
                            KpiCard("DPO", "${state.dpo} Days", "Payables", Modifier.weight(1f))
                            KpiCard("Runway", "${(state.totalBalance / state.cashBurnRate).toInt()} Days", "Cash Burn", Modifier.weight(1f))
                        }
                    }

                    // FX RISK ANALYSIS SECTION
                    item {
                        SectionHeader("FX Risk Analysis")
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            KpiCard("CaR", formatCurrency(state.currencyAtRisk), "Currency at Risk", Modifier.weight(1f))
                            KpiCard("Hedge", "${(state.hedgingRatio * 100).toInt()}%", "Hedging Ratio", Modifier.weight(1f))
                        }
                    }

                    if (state.executionLogs.isNotEmpty()) {
                        item {
                            SectionHeader("Autonomous Execution Tracking")
                            ExecutionLogsSection(state.executionLogs)
                        }
                    }

                    item {
                        SectionHeader("Autonomous Agents")
                        AgentStatusRow(state.agents)
                    }

                    items(state.bankAccounts) { account ->
                        BankAccountItem(account)
                    }

                    item {
                        SectionHeader("AI Recommendations")
                    }

                    items(state.recommendations) { recommendation ->
                        RecommendationItem(
                            recommendation = recommendation,
                            canApprove = user?.role == UserRole.CFO || user?.role == UserRole.ADMIN,
                            onApprove = { 
                                if (BiometricHelper.isBiometricAvailable(context)) {
                                    BiometricHelper.showBiometricPrompt(
                                        activity = context as FragmentActivity,
                                        onSuccess = { viewModel.approveRecommendation(recommendation.id) },
                                        onError = { /* Handle Error */ }
                                    )
                                } else {
                                    viewModel.approveRecommendation(recommendation.id)
                                }
                            },
                            onReject = { viewModel.rejectRecommendation(recommendation.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AlertItem(alert: TreasuryAlert) {
    val bgColor = when(alert.severity) {
        AlertSeverity.CRITICAL -> ErrorRed.copy(alpha = 0.1f)
        AlertSeverity.WARNING -> WarningOrange.copy(alpha = 0.1f)
        AlertSeverity.INFO -> SuccessGreen.copy(alpha = 0.1f)
    }
    val icon = when(alert.severity) {
        AlertSeverity.CRITICAL -> Icons.Default.Warning
        AlertSeverity.WARNING -> Icons.Default.PriorityHigh
        AlertSeverity.INFO -> Icons.Default.Info
    }
    val tint = when(alert.severity) {
        AlertSeverity.CRITICAL -> ErrorRed
        AlertSeverity.WARNING -> WarningOrange
        AlertSeverity.INFO -> SuccessGreen
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = tint)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(alert.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                Text(alert.description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun LiquidityDistributionPieChart(accounts: List<BankAccount>) {
    if (accounts.isEmpty()) return
    
    val total = accounts.sumOf { it.currentBalance }.toFloat()
    val colors = listOf(Color(0xFF1976D2), Color(0xFF388E3C), Color(0xFFFBC02D), Color(0xFFD32F2F))
    
    Card(
        modifier = Modifier.size(150.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            var startAngle = -90f
            accounts.forEachIndexed { index, account ->
                val sweepAngle = (account.currentBalance.toFloat() / total) * 360f
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    size = Size(size.width, size.height)
                )
                startAngle += sweepAngle
            }
        }
    }
}

@Composable
fun LiquidityTrendChart(forecasts: List<CashFlowForecast>) {
    if (forecasts.isEmpty()) return
    
    Card(
        modifier = Modifier.fillMaxWidth().height(150.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        val points = forecasts.map { it.predictedBalance.toFloat() }
        val max = points.maxOrNull() ?: 1f
        val min = points.minOrNull() ?: 0f
        val range = (max - min).coerceAtLeast(1f)
        
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val width = size.width
            val height = size.height
            val path = Path()
            
            points.forEachIndexed { index, value ->
                val x = if (points.size > 1) index * (width / (points.size - 1)) else width / 2
                val y = height - ((value - min) / range * height)
                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            
            drawPath(
                path = path,
                color = SuccessGreen,
                style = Stroke(width = 3.dp.toPx())
            )
        }
    }
}

@Composable
fun KpiCard(title: String, value: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

@Composable
fun AiCommandCenter(prompt: String, lastResponse: String?, onPromptChange: (String) -> Unit, onSend: () -> Unit) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = prompt,
                    onValueChange = onPromptChange,
                    placeholder = { Text("Enter command (e.g. 'Optimize cash for 90 days')", style = MaterialTheme.typography.bodySmall) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = onSend) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
        
        AnimatedVisibility(visible = lastResponse != null) {
            lastResponse?.let {
                Card(
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(it, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun ExecutionLogsSection(logs: List<ExecutionLog>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            logs.take(3).forEach { log ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "> ${log.message}",
                        style = MaterialTheme.typography.labelSmall,
                        color = when(log.status) {
                            ExecutionStatus.SUCCESS -> SuccessGreen
                            ExecutionStatus.FAILED -> ErrorRed
                            else -> Color.Gray
                        }
                    )
                    Text(
                        text = log.status.name,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun TotalBalanceCard(balance: Double, treasuryYield: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Total Liquidity", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = formatCurrency(balance),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (treasuryYield > 0) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Treasury Yield (MTD)", style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = "+${formatCurrency(treasuryYield)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AgentStatusRow(agents: List<AiAgent>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        items(agents) { agent ->
            FilterChip(
                selected = agent.status == AgentStatus.ACTIVE,
                onClick = { },
                label = { Text(agent.agentName) },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                if (agent.status == AgentStatus.ACTIVE) SuccessGreen else Color.Gray,
                                shape = MaterialTheme.shapes.extraSmall
                            )
                    )
                }
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun BankAccountItem(account: BankAccount) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(account.bankName ?: "Unknown Bank", fontWeight = FontWeight.Bold)
                Text(account.accountNumber ?: "XXXX-XXXX", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                formatCurrency(account.currentBalance),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun RecommendationItem(
    recommendation: TreasuryRecommendation,
    canApprove: Boolean,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    var showReasoning by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                color = when (recommendation.status) {
                                    RecommendationStatus.PENDING -> WarningOrange
                                    RecommendationStatus.APPROVED -> SuccessGreen
                                    RecommendationStatus.REJECTED -> ErrorRed
                                    RecommendationStatus.EXECUTED -> Color.Blue
                                },
                                shape = MaterialTheme.shapes.small
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(recommendation.type.name.replace("_", " "), fontWeight = FontWeight.Bold)
                }
                
                Text(
                    text = recommendation.status.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(recommendation.recommendationText ?: "", style = MaterialTheme.typography.bodyMedium)
            
            if (recommendation.reasoning.isNotEmpty()) {
                TextButton(
                    onClick = { showReasoning = !showReasoning },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = if (showReasoning) "Hide Multi-Agent Deliberation" else "View Multi-Agent Deliberation",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                if (showReasoning) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(8.dp)
                    ) {
                        recommendation.reasoning.forEach { thought ->
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    text = "${thought.agentName}: ",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = thought.thought,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            recommendation.expectedReturn?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Expected Return: ${String.format("%.2f", it)}%",
                    color = SuccessGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            if (recommendation.status == RecommendationStatus.PENDING) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onReject, colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed)) {
                        Text("Reject")
                    }
                    if (canApprove) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = onApprove, colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)) {
                            Text("Approve")
                        }
                    }
                }
            }
        }
    }
}

fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return format.format(amount)
}

fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(date)
}
