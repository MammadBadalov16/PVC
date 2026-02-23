package com.mb.pvc.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val width: Double,
    val height: Double,
    val foot: Double,
    val h: Double,
    val radius: Double,
    val arcLength: Double,
    val totalLength: Double,
    val price: Double,
    val timestamp: Long = System.currentTimeMillis()
)
