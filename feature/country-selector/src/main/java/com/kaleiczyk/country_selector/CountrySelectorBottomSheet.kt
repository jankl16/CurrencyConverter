package com.kaleiczyk.country_selector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaleiczyk.country_selector.ui.CountriesList
import com.kaleiczyk.theme.CurrencyConverterTheme
import com.kaleiczyk.theme.Grey
import com.kaleiczyk.theme.LightGreyText
import com.kaleiczyk.theme.White
import com.kaleiczyk.theme.model.CountryTile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CountrySelectorBottomSheet(
    type: CountrySelectorType,
    onCountrySelected: (CountryTile) -> Unit
) {
    val viewModel = viewModel<CountrySelectorViewModel>()
    val state = viewModel.state.collectAsState()

    CountrySelectorBottomSheet(
        type = type,
        countries = state.value.countries,
        query = state.value.query,
        onQueryChanged = viewModel::onQueryChanged,
        onCountrySelected = onCountrySelected
    )
}

@Composable
private fun CountrySelectorBottomSheet(
    type: CountrySelectorType,
    countries: ImmutableList<CountryTile>,
    query: String,
    onQueryChanged: (String) -> Unit,
    onCountrySelected: (CountryTile) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when (type) {
                CountrySelectorType.SELECT_SENDER -> "Receiving from" // TODO: use string res
                CountrySelectorType.SELECT_RECEIVER -> "Sending to"
            },
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W700)
        )
        Spacer(modifier = Modifier.height(20.dp))
        SearchTextField(query, onQueryChanged)
        Spacer(modifier = Modifier.height(20.dp))
        CountriesList(countries, onCountrySelected)
    }
}

@Composable
private fun SearchTextField(query: String, onQueryChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = query,
        onValueChange = onQueryChanged,
        leadingIcon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = com.kaleiczyk.theme.R.drawable.ic_search),
                tint = Grey,
                contentDescription = null
            )
        },
        label = {
            Text(
                "Search", // TODO: use string resource
                style = MaterialTheme.typography.bodySmall.copy(color = LightGreyText)
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = LightGreyText),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Grey)
    )
}

@Preview
@Composable
fun CountrySelectorBottomSheetPreview() {
    CurrencyConverterTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            CountrySelectorBottomSheet(
                type = CountrySelectorType.SELECT_SENDER,
                countries = CountryTile.entries.toImmutableList(),
                query = "Pol",
                onQueryChanged = {},
                onCountrySelected = {}
            )
        }
    }
}
