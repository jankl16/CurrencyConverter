package com.kaleiczyk.country_selector.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaleiczyk.country_selector.R
import com.kaleiczyk.theme.CurrencyConverterTheme
import com.kaleiczyk.theme.LightGrey
import com.kaleiczyk.theme.LightGreyText
import com.kaleiczyk.theme.White
import com.kaleiczyk.theme.model.CountryTile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun CountriesList(
    countries: ImmutableList<CountryTile>,
    onItemTapAction: (CountryTile) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.country_selector_countries_list_title),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700)
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(countries, key = { it.ordinal }) { country ->
                key(country.ordinal) {
                    CountryTile(country, onItemTapAction)
                }
            }
        }
    }
}

@Composable
private fun CountryTile(country: CountryTile, onItemTapAction: (CountryTile) -> Unit) {
    Column(modifier = Modifier.clickable {
        onItemTapAction(country)
    }) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .height(48.dp)
        ) {
            CountryTileFlag(country)
            Spacer(modifier = Modifier.width(16.dp))
            CountryTileInfo(country)
        }
        HorizontalDivider(color = LightGrey)
    }
}

@Composable
private fun CountryTileInfo(country: CountryTile) {
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
        Text(
            text = stringResource(id = country.title),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${stringResource(id = country.currency.title)} • ${stringResource(id = country.currency.abbreviation)}",
            style = MaterialTheme.typography.bodyMedium.copy(color = LightGreyText)
        )
    }
}

@Composable
private fun CountryTileFlag(country: CountryTile) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(48.dp))
            .background(LightGrey)
            .size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = country.flag),
            contentDescription = null
        )
    }
}

@Preview()
@Composable
private fun CountriesListPreview() {
    CurrencyConverterTheme {
        Box(modifier = Modifier.background(White)) {
            CountriesList(CountryTile.entries.toImmutableList(), onItemTapAction = {})
        }
    }
}
