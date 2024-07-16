package com.kaleiczyk.network.di

import com.kaleiczyk.network.transferGo.RetrofitTransferGoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object RetrofitModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val jsonConverterFactory = Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())

        return Retrofit.Builder()
            .baseUrl("https://my.transfergo.com/api/")
            .addConverterFactory(jsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitTransferGoApi(retrofit: Retrofit): RetrofitTransferGoApi {
        return retrofit.create<RetrofitTransferGoApi>()
    }
}
