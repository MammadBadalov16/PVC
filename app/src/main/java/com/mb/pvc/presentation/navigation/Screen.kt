package com.mb.pvc.presentation.navigation

sealed class Screen(val route: String) {
    object Calculator : Screen("calculator")
    object Orders : Screen("orders")
    object Reports : Screen("reports")
    object Settings : Screen("settings")
}
