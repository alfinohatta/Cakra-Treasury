package com.example.cakratreasury

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SettingsInputComponent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cakratreasury.domain.model.User
import com.example.cakratreasury.ui.audit.AuditLogScreen
import com.example.cakratreasury.ui.auth.AuthViewModel
import com.example.cakratreasury.ui.auth.LoginScreen
import com.example.cakratreasury.ui.dashboard.DashboardScreen
import com.example.cakratreasury.ui.integrations.IntegrationScreen
import com.example.cakratreasury.ui.navigation.Screen
import com.example.cakratreasury.ui.policy.PolicyScreen
import com.example.cakratreasury.ui.scenario.ScenarioScreen
import com.example.cakratreasury.ui.theme.CakraTreasuryTheme
import com.example.cakratreasury.ui.transactions.TransactionsScreen
import com.example.cakratreasury.ui.working_capital.WorkingCapitalScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CakraTreasuryTheme {
                val currentUser by authViewModel.currentUser.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (currentUser == null) {
                        LoginScreen(onLoginSuccess = { /* State will update automatically via collectAsState */ })
                    } else {
                        MainScreen(
                            user = currentUser,
                            onLogout = { authViewModel.logout() }
                        )
                    }
                }
            }
        }
    }
}

data class NavigationItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
)

@Composable
fun MainScreen(user: User?, onLogout: () -> Unit) {
    val navController = rememberNavController()
    val items = listOf(
        NavigationItem(Screen.Dashboard, Icons.Default.Dashboard, "Dashboard"),
        NavigationItem(Screen.Transactions, Icons.Default.History, "History"),
        NavigationItem(Screen.WorkingCapital, Icons.Default.AccountBalanceWallet, "Capital"),
        NavigationItem(Screen.Scenario, Icons.Default.Analytics, "Scenario"),
        NavigationItem(Screen.Integrations, Icons.Default.SettingsInputComponent, "Connect"),
        NavigationItem(Screen.Policy, Icons.Default.Policy, "Policy"),
        NavigationItem(Screen.AuditLog, Icons.Default.Security, "Audit")
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.screen.route) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) { 
                DashboardScreen(
                    user = user,
                    onLogout = onLogout
                ) 
            }
            composable(Screen.Transactions.route) { TransactionsScreen() }
            composable(Screen.WorkingCapital.route) { WorkingCapitalScreen() }
            composable(Screen.Scenario.route) { ScenarioScreen() }
            composable(Screen.Integrations.route) { IntegrationScreen() }
            composable(Screen.Policy.route) { PolicyScreen() }
            composable(Screen.AuditLog.route) { AuditLogScreen() }
        }
    }
}
