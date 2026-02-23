package com.mb.pvc.domain.repository

import com.mb.pvc.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun saveOrder(order: OrderEntity)
    fun getAllOrders(): Flow<List<OrderEntity>>
    suspend fun deleteOrder(id: Int)
}
