package com.kaleiczyk.domain

import com.kaleiczyk.domain.errorProvider.GlobalErrorProvider
import com.kaleiczyk.domain.fake.FakeTransferGoApi
import com.kaleiczyk.domain.model.Result
import com.kaleiczyk.model.Currency
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ConvertCurrencyUseCaseTest {

    private val transferGoApi = FakeTransferGoApi()
    private val usecase = ConvertCurrencyUseCase(transferGoApi, GlobalErrorProvider())

    @Before
    fun setup() {
        transferGoApi.rate = DEFAULT_RATE
    }

    @Test
    fun test_convert_currency() = runTest {
        val amount = 100.0
        val result = usecase(amount, Currency.GBP, Currency.UAH)
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(DEFAULT_RATE, data.rate, DELTA)
        assertEquals(amount * DEFAULT_RATE, data.amount, DELTA)

    }

    private companion object {
        const val DEFAULT_RATE = 4.0
        const val DELTA = 0.001
    }
}