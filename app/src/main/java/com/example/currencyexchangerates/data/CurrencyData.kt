package com.example.currencyexchangerates.data

import android.icu.util.Currency

data class CurrencyData(
    val currency: Currency = Currency.getInstance("EUR"),
    val amount: String = "0"
)
