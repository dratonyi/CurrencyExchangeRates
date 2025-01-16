package com.example.currencyexchangerates.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyexchangerates.ui.components.CurrencyCard
import com.example.currencyexchangerates.ui.components.TopBar
import com.example.currencyexchangerates.ui.theme.MyAppTheme

@Composable
fun MainBackground() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TopBar()
        Spacer(modifier = Modifier.height(50.dp))
        CurrencyCard()
        Spacer(modifier = Modifier.height(50.dp))
        CurrencyCard()
    }

}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MainBackgroundPreview() {
    MyAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // This will apply your Compose background
        ) {
            MainBackground()
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun MainBackgroundLightPreview() {
    MyAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // This will apply your Compose background
        ) {
            MainBackground()
        }
    }
}