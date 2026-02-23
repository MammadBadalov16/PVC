package com.mb.pvc.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mb.pvc.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    var unitPrice by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            repository.getUnitPrice().collectLatest { price ->
                unitPrice = price.toString()
            }
        }
    }

    fun onUnitPriceChange(newValue: String) {
        unitPrice = newValue
    }

    fun saveSettings() {
        val price = unitPrice.toDoubleOrNull() ?: return
        viewModelScope.launch {
            repository.saveUnitPrice(price)
        }
    }
}
