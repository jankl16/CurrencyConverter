package com.kaleiczyk.model

enum class Country(
    private val currency: Currency
) {
    POLAND(Currency.PLN),
    GERMANY(Currency.EUR),
    GREAT_BRITAIN(Currency.GBP),
    UKRAINE(Currency.UAH)
}