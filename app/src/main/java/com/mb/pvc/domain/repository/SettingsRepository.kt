package com.mb.pvc.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getUnitPrice(): Flow<Double>
    suspend fun saveUnitPrice(price: Double)
}
