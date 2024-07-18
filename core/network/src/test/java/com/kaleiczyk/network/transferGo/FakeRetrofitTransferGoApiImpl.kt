package com.kaleiczyk.network.transferGo

import com.kaleiczyk.network.transferGo.model.CurrencyConverterResultDTO
import com.kaleiczyk.network.transferGo.model.CurrencyDTO
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Response

internal class FakeRetrofitTransferGoApiImpl : RetrofitTransferGoApi {

    var rate: Double = DEFAULT_RATE
    var errorCode: Int? = DEFAULT_ERROR_CODE

    override suspend fun convertCurrency(
        from: CurrencyDTO,
        to: CurrencyDTO,
        amount: Double
    ): Response<CurrencyConverterResultDTO> {

        return if (errorCode == null) {
            Response.success(
                CurrencyConverterResultDTO(
                    rate,
                    amount * rate
                )
            )
        } else {
            Response.error(
                errorCode ?: 0,
                ResponseBody.create(JSON_CONTENT_TYPE.toMediaType(), "")
            )
        }
    }

    private companion object {
        const val DEFAULT_RATE = 2.0
        val DEFAULT_ERROR_CODE: Int? = null
        const val JSON_CONTENT_TYPE = "json/application"
    }
}