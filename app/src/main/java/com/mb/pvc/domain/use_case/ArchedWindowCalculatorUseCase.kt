package com.mb.pvc.domain.use_case

import com.mb.pvc.domain.model.ArchedWindowCalculationError
import com.mb.pvc.domain.model.ArchedWindowCalculationResult
import com.mb.pvc.domain.model.Resource
import com.mb.pvc.domain.service.ArchedWindowCalculator
import javax.inject.Inject


class ArchedWindowCalculatorUseCase @Inject constructor(
    private val archedWindowCalculator: ArchedWindowCalculator
) {
    fun execute(
        widthStr: String,
        heightStr: String,
        footStr: String
    ): Resource<ArchedWindowCalculationResult> {

        if (widthStr.isBlank() || heightStr.isBlank() || footStr.isBlank())
            return Resource.Error("Məlumatları daxil edin!")

        val width = widthStr.toDoubleOrNull()
        val height = heightStr.toDoubleOrNull()
        val foot = footStr.toDoubleOrNull()

        if (width == null || height == null || foot == null)
            return Resource.Error("Düzgün ədəd daxil edin!")

        val result = archedWindowCalculator.calculate(width, height, foot)

        return result.fold(
            onSuccess = { Resource.Success(it) },
            onFailure = { error -> Resource.Error(error.message ?: "Unknown error") }
        )
    }
}