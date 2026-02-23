package com.mb.pvc.presentation.calculator

sealed class ArchedWindowEvent {
    data class WidthChanged(val value: String) : ArchedWindowEvent()
    data class HeightChanged(val value: String) : ArchedWindowEvent()
    data class FootChanged(val value: String) : ArchedWindowEvent()
    data class QuantityChanged(val value: String) : ArchedWindowEvent()

    object CalculateClicked : ArchedWindowEvent()
    object SaveOrderClicked : ArchedWindowEvent()
    object ResetClicked : ArchedWindowEvent()
}
