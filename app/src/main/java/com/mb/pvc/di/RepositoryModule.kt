package com.mb.pvc.di

import com.mb.pvc.data.repository.OrderRepositoryImpl
import com.mb.pvc.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository
}
