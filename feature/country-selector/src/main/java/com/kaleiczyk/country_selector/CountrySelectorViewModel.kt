package com.kaleiczyk.country_selector

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.kaleiczyk.domain.GetCountriesUseCase
import com.kaleiczyk.theme.model.CountryTile
import com.kaleiczyk.theme.model.toCountryTile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@Immutable
internal data class State(
    val countries: ImmutableList<CountryTile>,
    val query: String = ""
)

@HiltViewModel
internal class CountrySelectorViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State(searchCountries()))
    val state = _state.asStateFlow()

    fun onQueryChanged(value: String) {
        _state.update { it.copy(query = value) }
        val countries = searchCountries(value)
        _state.update { it.copy(countries = countries) }
    }

    private fun searchCountries(query: String = "") = getCountriesUseCase(query).map {
        it.toCountryTile()
    }.toImmutableList()
}