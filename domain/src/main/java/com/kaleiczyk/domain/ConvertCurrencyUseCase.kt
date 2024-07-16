package com.kaleiczyk.domain

import com.kaleiczyk.model.Currency
import com.kaleiczyk.model.CurrencyConvertResult
import com.kaleiczyk.model.RequestResult
import com.kaleiczyk.network.transferGo.TransferGoApi
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val api: TransferGoApi,
) {

    suspend operator fun invoke(
        amount: Double,
        from: Currency,
        to: Currency
    ): Result<CurrencyConvertResult> {
        val errorMessage = checkAmountLimit(amount, from)
        if (errorMessage != null) return Result.Error(errorMessage)

        return when (val result = api.convertCurrency(from, to, amount)) {
            is RequestResult.Success -> Result.Success(result.data)
            is RequestResult.Error -> when (result) {
                is RequestResult.Error.ServerError, is RequestResult.Error.Network, is RequestResult.Error.UnknownHttp -> {
                    TODO("SEND TO GLOBAL ERROR")
                }

                else -> Result.Error(result.message)
            }
        }
    }

    private fun checkAmountLimit(amount: Double, currency: Currency): String? {
        val maxAmountLimit = currency.getMaxAmountLimit()
        return when {
            amount <= 0 -> "Minimum sending amount is: $MIN_AMOUNT ${currency.code.uppercase()}"
            amount > maxAmountLimit -> "Maximum sending amount: $amount ${currency.code.uppercase()}"
            else -> null
        }
    }

    private fun Currency.getMaxAmountLimit() = when (this) {
        Currency.PLN -> MAX_AMOUNT_LIMIT_PLN
        Currency.EUR -> MAX_AMOUNT_LIMIT_EUR
        Currency.GBP -> MAX_AMOUNT_LIMIT_GBP
        Currency.UAH -> MAX_AMOUNT_LIMIT_UAH
    }

    companion object {
        private const val MAX_AMOUNT_LIMIT_PLN = 20000
        private const val MAX_AMOUNT_LIMIT_EUR = 5000
        private const val MAX_AMOUNT_LIMIT_GBP = 1000
        private const val MAX_AMOUNT_LIMIT_UAH = 500000
        private const val MIN_AMOUNT = 1
    }
}