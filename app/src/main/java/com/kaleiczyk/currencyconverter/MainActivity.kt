package com.kaleiczyk.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaleiczyk.domain.model.GlobalError
import com.kaleiczyk.feature_converting.CurrencyConvertingScreen
import com.kaleiczyk.theme.Black
import com.kaleiczyk.theme.CurrencyConverterTheme
import com.kaleiczyk.theme.Grey
import com.kaleiczyk.theme.LightGreyText
import com.kaleiczyk.theme.Shadow
import com.kaleiczyk.theme.White
import com.kaleiczyk.theme.dropShadow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White)
                ) { innerPadding ->
                    val globalError by viewModel.globalErrorProvider.globalError.collectAsState(null)
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .safeDrawingPadding()
                    ) {
                        CurrencyConvertingScreen()
                        globalError?.let {
                            GlobalErrorNotification(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GlobalErrorNotification(error: GlobalError, modifier: Modifier = Modifier) {
    var showValue by remember(error) { mutableStateOf(true) }
    val alpha by animateFloatAsState(
        targetValue = if (showValue) 1f else 0f,
        label = "globalNotificationVisibility"
    )
    LaunchedEffect(key1 = error) {
        delay(3000)
        showValue = false
    }

    val shape = RoundedCornerShape(8.dp)
    if (showValue) {
        Row(
            modifier = modifier
                .alpha(alpha)
                .padding(horizontal = 20.dp)
                .dropShadow(shape, Shadow, blur = 16.dp, 0.dp, 0.dp, 0.dp)
                .clip(shape)
                .background(White)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                modifier = Modifier
                    .height(36.dp)
                    .width(32.dp),
                painter = painterResource(id = R.drawable.ic_global_error),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = error.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Black,
                        fontWeight = FontWeight.W700
                    )
                )
                Text(
                    text = error.message,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = LightGreyText,
                    )
                )
            }
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showValue = false },
                painter = painterResource(id = R.drawable.ic_close),
                tint = Grey,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CurrencyConverterTheme {
        Box(modifier = Modifier.padding(vertical = 20.dp)) {
            GlobalErrorNotification(GlobalError("No network", "Check your internet connection"))
        }
    }
}