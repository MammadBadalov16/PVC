package com.mb.pvc.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Calculator : BottomNavItem(
        route = Screen.Calculator.route,
        title = "Hesabla",
        icon = Icons.Default.Calculate
    )

    object Orders : BottomNavItem(
        route = Screen.Orders.route,
        title = "Sifarişlər",
        icon = Icons.Default.List
    )

    object Reports : BottomNavItem(
        route = Screen.Reports.route,
        title = "Hesabat",
        icon = Icons.Default.PieChart
    )

    companion object {
        val items = listOf(
            Calculator,
            Orders,
            Reports
        )
    }
}
