package com.mb.pvc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mb.pvc.data.local.dao.OrderDao
import com.mb.pvc.data.local.entity.OrderEntity

@Database(entities = [OrderEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}
