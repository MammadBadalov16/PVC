package com.mb.pvc.data.repository

import com.mb.pvc.data.local.dao.OrderDao
import com.mb.pvc.data.local.entity.OrderEntity
import com.mb.pvc.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val dao: OrderDao
) : OrderRepository {
    override suspend fun saveOrder(order: OrderEntity) {
        dao.insertOrder(order)
    }

    override suspend fun updateOrder(order: OrderEntity) {
        dao.updateOrder(order)
    }

    override fun getAllOrders(): Flow<List<OrderEntity>> {
        return dao.getAllOrders()
    }

    override suspend fun deleteOrder(id: Int) {
        dao.deleteOrder(id)
    }
}
