package com.kaleiczyk.network.transferGo.model

import com.kaleiczyk.model.Currency

fun CurrencyDTO.toCurrency(): Currency = when (this) {
    CurrencyDTO.PLN -> Currency.PLN
    CurrencyDTO.EUR -> Currency.EUR
    CurrencyDTO.GBR -> Currency.GBP
    CurrencyDTO.UAH -> Currency.UAH
}

fun Currency.toCurrencyDTO(): CurrencyDTO = when (this) {
    Currency.PLN -> CurrencyDTO.PLN
    Currency.EUR -> CurrencyDTO.EUR
    Currency.GBP -> CurrencyDTO.GBR
    Currency.UAH -> CurrencyDTO.UAH
}