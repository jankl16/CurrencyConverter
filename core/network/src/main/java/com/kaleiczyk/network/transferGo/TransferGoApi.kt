package com.kaleiczyk.network.transferGo

import com.kaleiczyk.model.Currency
import com.kaleiczyk.model.CurrencyConvertResult
import com.kaleiczyk.model.RequestResult

interface TransferGoApi {
    suspend fun convertCurrency(
        from: Currency,
        to: Currency,
        amount: Double
    ): RequestResult<CurrencyConvertResult>
}
