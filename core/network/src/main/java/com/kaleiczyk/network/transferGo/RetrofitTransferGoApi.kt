package com.kaleiczyk.network.transferGo

import com.kaleiczyk.network.transferGo.model.CurrencyConverterResultDTO
import com.kaleiczyk.network.transferGo.model.CurrencyDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitTransferGoApi {
    @GET("fx-rates")
    suspend fun convertCurrency(
        @Query("from") from: CurrencyDTO,
        @Query("to") to: CurrencyDTO,
        @Query("amount") amount: Double
    ): Response<CurrencyConverterResultDTO>
}
