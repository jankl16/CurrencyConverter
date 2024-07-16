package com.kaleiczyk.network.transferGo

import com.kaleiczyk.model.Currency
import com.kaleiczyk.model.CurrencyConvertResult
import com.kaleiczyk.model.RequestResult
import com.kaleiczyk.network.transferGo.model.toCurrencyConvertResult
import com.kaleiczyk.network.transferGo.model.toCurrencyDTO
import com.kaleiczyk.network.utils.sendRetrofitRequest
import javax.inject.Inject

internal class TransferGoApiImpl @Inject constructor(
    private val api: RetrofitTransferGoApi
) : TransferGoApi {
    override suspend fun convertCurrency(
        from: Currency,
        to: Currency,
        amount: Double
    ): RequestResult<CurrencyConvertResult> {
        return sendRetrofitRequest(
            request = {
                api.convertCurrency(from.toCurrencyDTO(), to.toCurrencyDTO(), amount)
            },
            mapper = {
                it.toCurrencyConvertResult()
            }
        )
    }
}
