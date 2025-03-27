package com.example.currencyexchangerates.ui.screens

import android.icu.util.Currency
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyexchangerates.UserEvent
import com.example.currencyexchangerates.data.CurrencyData
import com.example.currencyexchangerates.ui.components.CurrencyCard
import com.example.currencyexchangerates.ui.components.TopBar
import com.example.currencyexchangerates.ui.theme.MyAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBackground(
    baseCurrency: CurrencyData,
    targetCurrency: CurrencyData,
    onEvent: (event: UserEvent) -> Unit,
    navToChooseCurrency: () -> Unit,
    getFlagId: (code: String) -> Int
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Home") },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },

        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                CurrencyCard(baseCurrency, "base", onEvent, navToChooseCurrency, getFlagId)
                Spacer(modifier = Modifier.height(50.dp))
                CurrencyCard(targetCurrency, "target", onEvent, navToChooseCurrency, getFlagId)
            }
        }
    )
}

/*@Preview(
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
            MainBackground(
                CurrencyData(),
                CurrencyData(),
                {/*TODO*/},
                {}
            )
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
            MainBackground(
                CurrencyData(),
                CurrencyData(),
                {/*TODO*/},
                {}
            )
        }
    }
}*/