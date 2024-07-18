package com.kaleiczyk.network.di

import com.kaleiczyk.network.transferGo.TransferGoApi
import com.kaleiczyk.network.transferGo.TransferGoApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class NetworkModule {
    @Binds
    @Singleton
    internal abstract fun provideTransferGoApi(impl: TransferGoApiImpl): TransferGoApi
}