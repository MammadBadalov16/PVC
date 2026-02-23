package com.mb.pvc.domain.service

import com.mb.pvc.domain.model.ArchedWindowCalculationError
import com.mb.pvc.domain.model.ArchedWindowCalculationResult
import javax.inject.Inject
import kotlin.math.PI
import kotlin.math.asin

class ArchedWindowCalculatorImpl @Inject constructor() : ArchedWindowCalculator {
    override fun calculate(width: Double, height: Double, foot: Double, unitPrice: Double): Result<ArchedWindowCalculationResult> {
        return try {
            val w = width * 10
            val hTotal = height * 10
            val footLen = foot * 10
            val h = hTotal - footLen

            if (h <= 0) return Result.failure(ArchedWindowCalculationError.InvalidHeight())
            if (h > w / 2) return Result.failure(ArchedWindowCalculationError.ArchedWindowTooHigh())

            val fourH = 4 * h
            val d = ((w * w) / fourH) + h
            val r = d / 2
            val arcsin = w / d
            val angle = 2 * Math.toDegrees(asin(arcsin))
            val l = (3.14 * r * angle) / 180

            val result = ArchedWindowCalculationResult(
                h = h / 10,
                radius = r / 10,
                arcLength = l / 10,
                foot = footLen / 5,
                total = (l / 10) + (footLen / 5),
                price = unitPrice * l / 1000
            )
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
