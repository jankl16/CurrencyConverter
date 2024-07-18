package com.kaleiczyk.network.transferGo

import com.kaleiczyk.model.Currency
import com.kaleiczyk.model.RequestResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class TransferGoApiImplTest {

    private val fakeRetrofitApi = FakeRetrofitTransferGoApiImpl()
    private val transferGoApiImpl = TransferGoApiImpl(fakeRetrofitApi)

    @Before
    fun setUp() {
        fakeRetrofitApi.rate = DEFAULT_RATE
    }

    @Test
    fun test_convert_currency_success() = runTest {
        val amount = 100.0
        val result = transferGoApiImpl.convertCurrency(Currency.EUR, Currency.PLN, amount)
        assertTrue(result is RequestResult.Success)

        val data = (result as RequestResult.Success).data
        assertEquals(DEFAULT_RATE, data.rate, DELTA)
        assertEquals(DEFAULT_RATE * amount, data.amount, DELTA)
    }

    @Test
    fun test_convert_currency_server_error() = runTest {
        fakeRetrofitApi.errorCode = 500
        val amount = 100.0
        val result = transferGoApiImpl.convertCurrency(Currency.EUR, Currency.PLN, amount)
        assertTrue(result is RequestResult.Error.ServerError)
    }

    private companion object {
        const val DEFAULT_RATE = 3.0
        const val DELTA = 0.001
    }
}