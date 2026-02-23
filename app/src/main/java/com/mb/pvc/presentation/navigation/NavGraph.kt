package com.mb.pvc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mb.pvc.presentation.calculator.CalculatorScreen
import com.mb.pvc.presentation.orders.OrderListScreen
import com.mb.pvc.presentation.reports.ReportScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Calculator.route,
        modifier = modifier
    ) {
        composable(Screen.Calculator.route) {
            CalculatorScreen()
        }
        composable(Screen.Orders.route) {
            OrderListScreen()
        }
        composable(Screen.Reports.route) {
            ReportScreen()
        }
    }
}
