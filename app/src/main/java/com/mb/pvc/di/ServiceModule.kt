package com.mb.pvc.di

import com.mb.pvc.domain.service.ArchedWindowCalculator
import com.mb.pvc.domain.service.ArchedWindowCalculatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindCalculator(
        impl: ArchedWindowCalculatorImpl
    ): ArchedWindowCalculator
}