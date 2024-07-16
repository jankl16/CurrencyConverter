package com.kaleiczyk.feature_converting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaleiczyk.feature_converting.model.CountryTile
import com.kaleiczyk.feature_converting.ui.CurrencyConvertingForm
import com.kaleiczyk.theme.Error
import com.kaleiczyk.theme.ErrorBackground


// RENAME
@Composable
fun CurrencyConvertingScreen(viewModel: ConvertingCurrencyViewModel) {
    val state = viewModel.state.collectAsState()

    CurrencyConvertingScreen(
        from = state.value.from,
        to = state.value.to,
        fromAmount = state.value.fromAmount,
        onFromAmountChanged = viewModel::onFromAmountChanged,
        toAmount = state.value.toAmount,
        rate = state.value.convertingRate,
        onFromCountryTapAction = {},
        onToCountryTapAction = {},
        onSwapTapAction = viewModel::onSwapTapAction
    )
}

@Composable
private fun CurrencyConvertingScreen(
    errorMessage: String? = null,
    from: CountryTile,
    to: CountryTile,
    fromAmount: Double,
    onFromAmountChanged: (String) -> Unit,
    toAmount: Double?,
    rate: Double?,
    onFromCountryTapAction: () -> Unit,
    onToCountryTapAction: () -> Unit,
    onSwapTapAction: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 88.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrencyConvertingForm(
                from = from,
                to = to,
                fromAmount = fromAmount,
                toAmount = toAmount,
                rate = rate,
                onSwapTapAction = onSwapTapAction,
                onFromCountryTapAction = onFromCountryTapAction,
                onToCountryTapAction = onToCountryTapAction,
                onFromAmountChanged = onFromAmountChanged,
                isError = errorMessage != null,
            )
            if (errorMessage != null) {
                Spacer(Modifier.height(20.dp))
                ErrorMessage(errorMessage)
            }
        }

    }
}


@Composable
private fun ErrorMessage(message: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = ErrorBackground,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(com.kaleiczyk.theme.R.drawable.ic_info),
                tint = Error,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = message, style = MaterialTheme.typography.bodySmall.copy(color = Error))
        }
    }
}

@Preview
@Composable
fun SenderScreenPreview() {
    MaterialTheme {
        Scaffold { padding ->
            Box(modifier = Modifier.padding(padding)) {
                CurrencyConvertingScreen(
                    from = CountryTile.POLAND,
                    to = CountryTile.UKRAINE,
                    fromAmount = 100.0,
                    toAmount = 1000.0,
                    rate = 10.0,
                    onSwapTapAction = {},
                    onFromCountryTapAction = {},
                    onFromAmountChanged = {},
                    onToCountryTapAction = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun SenderScreenWithErrorPreview() {
    MaterialTheme {
        Scaffold { padding ->
            Box(modifier = Modifier.padding(padding)) {
                CurrencyConvertingScreen(
                    errorMessage = "Maximum sending amount: 20 000 UAH",
                    from = CountryTile.POLAND,
                    to = CountryTile.UKRAINE,
                    fromAmount = 100000.0,
                    toAmount = 1000000.0,
                    rate = 10.0,
                    onSwapTapAction = {},
                    onFromCountryTapAction = {},
                    onFromAmountChanged = {},
                    onToCountryTapAction = {}
                )
            }
        }
    }
}

