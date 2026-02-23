package com.mb.pvc.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Calculator : BottomNavItem("calculator", "Hesabla", Icons.Default.Calculate)
    object Orders : BottomNavItem("orders", "Sifarişlər", Icons.Default.List)
    object Reports : BottomNavItem("reports", "Hesabat", Icons.Default.BarChart)
    object Settings : BottomNavItem("settings", "Ayarlar", Icons.Default.Settings)

    companion object {
        val items = listOf(Calculator, Orders, Reports, Settings)
    }
}
