package com.example.cakratreasury.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Transactions : Screen("transactions")
    object WorkingCapital : Screen("working_capital")
    object Scenario : Screen("scenario")
    object AuditLog : Screen("audit_log")
    object Integrations : Screen("integrations")
    object Policy : Screen("policy")
}
