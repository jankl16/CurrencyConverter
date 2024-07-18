package com.kaleiczyk.feature_converting

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleiczyk.country_selector.CountrySelectorType
import com.kaleiczyk.domain.ConvertCurrencyUseCase
import com.kaleiczyk.domain.model.Result
import com.kaleiczyk.model.utils.isValidAmount
import com.kaleiczyk.model.utils.roundToTwoDecimals
import com.kaleiczyk.theme.model.CountryTile
import com.kaleiczyk.theme.model.toCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Immutable
internal data class State(
    val from: CountryTile = CountryTile.POLAND,
    val to: CountryTile = CountryTile.UKRAINE,
    val fromAmount: Double = INITIAL_FROM_AMOUNT,
    val toAmount: Double? = null,
    val convertingRate: Double? = null,
    val errorMessage: String? = null,
    val bottomSheetCountrySelectorType: CountrySelectorType? = null,
)

@HiltViewModel
internal class CurrencyConvertingViewModel @Inject constructor(
    private val currencyConverterUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        sendConvertRequest()
    }

    fun onSwapTapAction() = with(state.value) {
        sendConvertRequest(from = to, to = from)
    }

    fun onFromAmountChanged(amount: String) {
        if (amount.isValidAmount()) {
            _state.update {
                it.copy(fromAmount = amount.toDouble())
            }
            sendConvertRequest()
        }
    }

    fun onFromCountryChanged(country: CountryTile) {
        val toCountry = when (state.value.to) {
            country -> state.value.from
            else -> state.value.to
        }

        sendConvertRequest(from = country, to = toCountry)
    }

    fun onToCountryChanged(country: CountryTile) {
        val fromCountry = when (state.value.from) {
            country -> state.value.to
            else -> state.value.from
        }

        sendConvertRequest(from = fromCountry, to = country)
    }

    fun onSelectFromCountryTapAction() {
        _state.update { it.copy(bottomSheetCountrySelectorType = CountrySelectorType.SELECT_SENDER) }
    }

    fun onSelectToCountryTapAction() {
        _state.update { it.copy(bottomSheetCountrySelectorType = CountrySelectorType.SELECT_RECEIVER) }
    }

    fun onDismissBottomSheet() {
        _state.update { it.copy(bottomSheetCountrySelectorType = null) }
    }

    private fun sendConvertRequest(
        from: CountryTile = state.value.from,
        to: CountryTile = state.value.to
    ) = viewModelScope.launch(Dispatchers.Main) {
        _state.update {
            it.copy(
                from = from,
                to = to,
                toAmount = null,
                convertingRate = null,
                errorMessage = null
            )
        }

        val result = withContext(Dispatchers.IO) {
            with(state.value) {
                currencyConverterUseCase.invoke(
                    fromAmount.roundToTwoDecimals(),
                    from.currency.toCurrency(),
                    to.currency.toCurrency()
                )
            }
        }

        _state.update {
            when (result) {
                is Result.Success -> it.copy(
                    toAmount = result.data.amount,
                    convertingRate = result.data.rate,
                    errorMessage = null
                )

                is Result.Error -> it.copy(errorMessage = result.message)
                is Result.Nothing -> it
            }
        }
    }
}

private const val INITIAL_FROM_AMOUNT = 100.0