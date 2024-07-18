package com.kaleiczyk.theme.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.kaleiczyk.model.Country
import com.kaleiczyk.theme.R

@Immutable
enum class CountryTile(
    @StringRes val title: Int,
    val currency: CurrencyTile,
    @DrawableRes val flag: Int,
) {
    POLAND(R.string.poland, CurrencyTile.PLN, R.drawable.ic_poland),
    GERMANY(R.string.germany, CurrencyTile.EUR, R.drawable.ic_germany),
    GREAT_BRITAIN(R.string.great_britain, CurrencyTile.GBP, R.drawable.ic_great_britain),
    UKRAINE(R.string.ukraine, CurrencyTile.UAH, R.drawable.ic_ukraine),
}

fun Country.toCountryTile() = when (this) {
    Country.POLAND -> CountryTile.POLAND
    Country.GERMANY -> CountryTile.GERMANY
    Country.GREAT_BRITAIN -> CountryTile.GREAT_BRITAIN
    Country.UKRAINE -> CountryTile.UKRAINE
}
