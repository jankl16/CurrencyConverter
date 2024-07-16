package com.kaleiczyk.network.transferGo.model

import com.kaleiczyk.model.CurrencyConvertResult
import com.kaleiczyk.model.utils.roundToTwoDecimals
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyConverterResultDTO(
    @SerialName("rate")
    val rate: Double,
    @SerialName("toAmount")
    val amount: Double
)

fun CurrencyConverterResultDTO.toCurrencyConvertResult() = CurrencyConvertResult(
    rate = rate.roundToTwoDecimals(),
    amount = amount.roundToTwoDecimals()
)