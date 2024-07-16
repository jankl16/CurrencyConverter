package com.kaleiczyk.feature_converting.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.kaleiczyk.model.Currency
import com.kaleiczyk.theme.R

@Immutable
enum class CurrencyTile(@StringRes val abbreviation: Int, @StringRes val title: Int) {
    PLN(R.string.pln_abbreviation, R.string.pln_title),
    EUR(R.string.eur_abbreviation, R.string.eur_title),
    GBP(R.string.gbp_abbreviation, R.string.gbp_title),
    UAH(R.string.uah_abbreviation, R.string.uah_title)
}

fun CurrencyTile.toCurrency(): Currency = when (this) {
    CurrencyTile.PLN -> Currency.PLN
    CurrencyTile.EUR -> Currency.EUR
    CurrencyTile.GBP -> Currency.GBP
    CurrencyTile.UAH -> Currency.UAH

}
