package com.mb.pvc.presentation.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mb.pvc.data.local.entity.OrderEntity
import com.mb.pvc.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    val orders: StateFlow<List<OrderEntity>> = repository.getAllOrders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateOrderQuantity(order: OrderEntity, newQuantity: Int) {
        if (newQuantity < 1) return
        viewModelScope.launch {
            repository.updateOrder(order.copy(quantity = newQuantity))
        }
    }

    fun deleteOrder(id: Int) {
        viewModelScope.launch {
            repository.deleteOrder(id)
        }
    }
}
