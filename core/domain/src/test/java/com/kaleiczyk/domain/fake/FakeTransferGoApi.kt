package com.kaleiczyk.domain.fake

import com.kaleiczyk.model.Currency
import com.kaleiczyk.model.CurrencyConvertResult
import com.kaleiczyk.model.RequestResult
import com.kaleiczyk.network.transferGo.TransferGoApi

class FakeTransferGoApi : TransferGoApi {

    var rate = 3.0

    override suspend fun convertCurrency(
        from: Currency,
        to: Currency,
        amount: Double,
    ): RequestResult<CurrencyConvertResult> {
        return RequestResult.Success(CurrencyConvertResult(rate, rate * amount), code = 200)
    }
}