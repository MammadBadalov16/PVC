package com.mb.pvc.domain.service

import com.mb.pvc.domain.model.ArchedWindowCalculationResult
interface ArchedWindowCalculator {
    fun calculate(width: Double, height: Double, foot: Double): Result<ArchedWindowCalculationResult>
}