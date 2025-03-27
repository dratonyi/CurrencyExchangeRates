package com.example.currencyexchangerates.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyexchangerates.UserEvent
import com.example.currencyexchangerates.ui.components.CurrencyListItem
import com.example.currencyexchangerates.ui.theme.MyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCurrency(
    onBackButton: () -> Unit,
    currencies: Map<String, String>,
    search: String,
    onEvent: (UserEvent) -> Unit,
    getFlagId: (code: String) -> Int
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Choose Currency") },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackButton() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "null",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },

        content = { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                //verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { onEvent(UserEvent.SearchCurrency(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                        .padding(horizontal = 15.dp),
                    shape = RoundedCornerShape(16.dp),
                    label = { Text(text = "Search") }
                )

                LazyColumn(
                    modifier = Modifier
                        //.padding(padding)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(15.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    itemsIndexed(currencies.entries.toList()) { index, currency ->
                        CurrencyListItem(currency, onEvent, onBackButton, getFlagId)
                    }
                }
            }
        }


    )


}

@Preview
@Composable
fun PreviewChooseCurrency() {
    MyAppTheme {
        //ChooseCurrency({}, Currency.getAvailableCurrencies().toList(), "", {})
    }
}