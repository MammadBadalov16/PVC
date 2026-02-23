package com.mb.pvc.presentation.calculator

import com.mb.pvc.domain.model.ArchedWindowCalculationResult

data class ArchedWindowUiState(
    val width: String = "",
    val height: String = "",
    val foot: String = "",
    val quantity: String = "1",
    val result: ArchedWindowCalculationResult? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)
