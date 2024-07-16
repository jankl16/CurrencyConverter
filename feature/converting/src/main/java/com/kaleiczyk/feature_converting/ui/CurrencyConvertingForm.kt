package com.kaleiczyk.feature_converting.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaleiczyk.feature_converting.R
import com.kaleiczyk.feature_converting.model.CountryTile
import com.kaleiczyk.feature_converting.model.CurrencyTile
import com.kaleiczyk.theme.Black
import com.kaleiczyk.theme.Error
import com.kaleiczyk.theme.LightGrey
import com.kaleiczyk.theme.LightGreyText
import com.kaleiczyk.theme.Shadow
import com.kaleiczyk.theme.Transparent
import com.kaleiczyk.theme.White
import com.kaleiczyk.theme.dropShadow

@Composable
internal fun CurrencyConvertingForm(
    from: CountryTile,
    to: CountryTile,
    fromAmount: Double,
    onFromAmountChanged: (String) -> Unit,
    toAmount: Double?,
    rate: Double?,
    onSwapTapAction: () -> Unit,
    onFromCountryTapAction: () -> Unit,
    onToCountryTapAction: () -> Unit,
    isError: Boolean,
) {
    ConstraintLayout {
        val (form, swapButton, currencyRate) = createRefs()

        CurrencyConvertingForm(
            modifier = Modifier.constrainAs(form) {
                linkTo(
                    top = parent.top,
                    bottom = parent.bottom,
                    start = parent.start,
                    end = parent.end
                )
            },
            from = from,
            to = to,
            fromAmount = fromAmount,
            onFromAmountChanged = onFromAmountChanged,
            onFromCountryTapAction = onFromCountryTapAction,
            onToCountryTapAction = onToCountryTapAction,
            toAmount = toAmount,
            isError = isError
        )
        SwapButton(modifier = Modifier.constrainAs(swapButton) {
            linkTo(
                top = parent.top,
                bottom = parent.bottom,
                start = parent.start,
                end = parent.end,
                startMargin = 48.dp,
                horizontalBias = 0f,
                verticalBias = 0.5f,
            )
        }, onSwapTapAction)
        if (rate != null) {
            CurrencyRate(
                modifier = Modifier.constrainAs(currencyRate) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        start = parent.start,
                        end = parent.end,
                    )
                },
                from = from.currency,
                to = to.currency,
                rate = rate
            )
        }
    }
}

@Composable
private fun CurrencyConvertingForm(
    modifier: Modifier = Modifier,
    from: CountryTile,
    to: CountryTile,
    fromAmount: Double,
    onFromAmountChanged: (String) -> Unit,
    toAmount: Double?,
    onFromCountryTapAction: () -> Unit,
    onToCountryTapAction: () -> Unit,
    isError: Boolean,
) {
    val borderRadius = 16.dp
    val shape = RoundedCornerShape(corner = CornerSize(borderRadius))

    Column(
        modifier = modifier
            .clip(shape = shape)
            .border(0.dp, Transparent, shape)
            .fillMaxWidth()
            .background(LightGrey),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .dropShadow(
                    color = Shadow,
                    shape = shape,
                    offsetY = 0.dp,
                    offsetX = 0.dp,
                    blur = 16.dp
                )
                .fillMaxWidth(),
            color = White,
            border = BorderStroke(
                2.dp,
                color = when {
                    isError -> Error
                    else -> Transparent
                }
            ),
            shape = shape
        ) {
            Carcass(
                title = stringResource(id = R.string.currency_converting_form_from_title),
                country = from,
                onCountryTapAction = onFromCountryTapAction,
                textContent = {
                    OutlinedTextField(
                        value = fromAmount.toString(),
                        onValueChange = onFromAmountChanged,
                        singleLine = true,

                        textStyle = MaterialTheme.typography.headlineLarge.copy(
                            color = when {
                                isError -> Error
                                else -> MaterialTheme.colorScheme.primary
                            },
                            fontWeight = FontWeight.W700,
                            textAlign = TextAlign.End,
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Transparent,
                            unfocusedBorderColor = Transparent,
                            focusedLabelColor = Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                },
            )
        }
        Carcass(
            title = stringResource(id = R.string.currency_converting_form_to_title),
            country = to,
            onCountryTapAction = onToCountryTapAction,
            textContent = {
                if (toAmount != null) {
                    Text(
                        text = toAmount.toString(),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Black,
                            fontWeight = FontWeight.W700
                        )
                    )
                }
            },
        )
    }
}

@Composable
private fun Carcass(
    title: String,
    country: CountryTile,
    onCountryTapAction: () -> Unit,
    textContent: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, style = MaterialTheme.typography.bodyMedium.copy(color = LightGreyText))
            CountrySelector(country, onCountryTapAction)
        }
        Box(modifier = Modifier.weight(1F), contentAlignment = Alignment.CenterEnd) {
            textContent()
        }
    }
}

@Composable
private fun CountrySelector(
    country: CountryTile,
    onCountryTapAction: () -> Unit,
) {
    Surface(onClick = onCountryTapAction, color = Transparent) {
        Row(
            Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(country.flag),
                modifier = Modifier.size(size = 32.dp),
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(id = country.currency.abbreviation),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700)
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.ic_chevron_down),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun SwapButton(modifier: Modifier = Modifier, onSwapTapAction: () -> Unit) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(24.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable(true, onClick = onSwapTapAction),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_reverse),
            contentDescription = null,
            tint = White
        )
    }
}

@Composable
private fun CurrencyRate(
    modifier: Modifier,
    from: CurrencyTile,
    to: CurrencyTile,
    rate: Double
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Black)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "1 ${stringResource(id = from.abbreviation)} = $rate ${stringResource(id = to.abbreviation)}",
            color = White,
            style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.W700, lineHeight = 14.sp)
        )
    }
}

@Preview
@Composable
fun SenderScreenPreview() {
    MaterialTheme {
        CurrencyConvertingForm(
            from = CountryTile.POLAND,
            to = CountryTile.UKRAINE,
            fromAmount = 100.0,
            toAmount = 1000.0,
            onFromAmountChanged = {},
            onSwapTapAction = {},
            rate = 10.0,
            onFromCountryTapAction = {},
            onToCountryTapAction = {},
            isError = false
        )
    }
}
