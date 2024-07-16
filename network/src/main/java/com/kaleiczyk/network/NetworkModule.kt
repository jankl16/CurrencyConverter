package com.kaleiczyk.network

import com.kaleiczyk.network.transferGo.TransferGoApi
import com.kaleiczyk.network.transferGo.TransferGoApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class NetworkModule {
    @Binds
    internal abstract fun provideTransferGoApi(impl: TransferGoApiImpl): TransferGoApi
}