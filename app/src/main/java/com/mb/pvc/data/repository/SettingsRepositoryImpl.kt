package com.mb.pvc.data.repository

import android.content.SharedPreferences
import com.mb.pvc.domain.repository.SettingsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences
) : SettingsRepository {

    companion object {
        private const val UNIT_PRICE_KEY = "unit_price"
        private const val DEFAULT_UNIT_PRICE = 3.0
    }

    override fun getUnitPrice(): Flow<Double> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { p, key ->
            if (key == UNIT_PRICE_KEY) {
                trySend(p.getFloat(UNIT_PRICE_KEY, DEFAULT_UNIT_PRICE.toFloat()).toDouble())
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        
        // İlk dəyəri göndəririk
        trySend(prefs.getFloat(UNIT_PRICE_KEY, DEFAULT_UNIT_PRICE.toFloat()).toDouble())

        awaitClose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.onStart {
        emit(prefs.getFloat(UNIT_PRICE_KEY, DEFAULT_UNIT_PRICE.toFloat()).toDouble())
    }

    override suspend fun saveUnitPrice(price: Double) {
        prefs.edit().putFloat(UNIT_PRICE_KEY, price.toFloat()).apply()
    }
}
