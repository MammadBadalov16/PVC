package com.mb.pvc.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Calculator : Screen("calculator", "Hesabla", Icons.Default.Calculate)
    object Orders : Screen("orders", "Sifarişlər", Icons.Default.List)
    object Reports : Screen("reports", "Hesabat", Icons.Default.PieChart)
}
