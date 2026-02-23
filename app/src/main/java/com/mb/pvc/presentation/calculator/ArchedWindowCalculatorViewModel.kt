package com.mb.pvc.presentation.calculator

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mb.pvc.data.local.entity.OrderEntity
import com.mb.pvc.domain.model.Resource
import com.mb.pvc.domain.repository.OrderRepository
import com.mb.pvc.domain.use_case.ArchedWindowCalculatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchedWindowCalculatorViewModel @Inject constructor(
    private val useCase: ArchedWindowCalculatorUseCase,
    private val repository: OrderRepository
) : ViewModel() {

    var state by mutableStateOf(ArchedWindowUiState())
        private set

    fun onEvent(event: ArchedWindowEvent) {
        when (event) {
            is ArchedWindowEvent.WidthChanged ->
                state = state.copy(width = event.value, message = null)

            is ArchedWindowEvent.HeightChanged ->
                state = state.copy(height = event.value, message = null)

            is ArchedWindowEvent.FootChanged ->
                state = state.copy(foot = event.value, message = null)

            ArchedWindowEvent.CalculateClicked ->
                calculate()

            ArchedWindowEvent.SaveOrderClicked ->
                saveOrder()

            ArchedWindowEvent.ResetClicked ->
                state = ArchedWindowUiState()
        }
    }

    private fun calculate() {
        state = state.copy(isLoading = true, error = null, message = null)

        viewModelScope.launch {
            when (val result = useCase.execute(state.width, state.height, state.foot)) {
                is Resource.Success ->
                    state = state.copy(
                        result = result.data,
                        isLoading = false
                    )

                is Resource.Error ->
                    state = state.copy(
                        error = result.message,
                        isLoading = false
                    )

                is Resource.Loading ->
                    state = state.copy(isLoading = true)
            }
        }
    }

    private fun saveOrder() {
        val result = state.result ?: return
        
        viewModelScope.launch {
            val order = OrderEntity(
                width = state.width.toDoubleOrNull() ?: 0.0,
                height = state.height.toDoubleOrNull() ?: 0.0,
                foot = state.foot.toDoubleOrNull() ?: 0.0,
                h = result.h,
                radius = result.radius,
                arcLength = result.arcLength,
                totalLength = result.total,
                price = result.price
            )
            repository.saveOrder(order)
            
            // Show success message and reset everything
            state = ArchedWindowUiState(message = "Sifariş uğurla yadda saxlanıldı!")
            
            // Clear message after 3 seconds
            delay(3000)
            if (state.message == "Sifariş uğurla yadda saxlanıldı!") {
                state = state.copy(message = null)
            }
        }
    }
}
