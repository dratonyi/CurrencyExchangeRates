package com.example.currencyexchangerates.data

import android.icu.util.Currency

data class CurrencyState(
    val base: CurrencyData = CurrencyData(),
    val targets: Map<CurrencyData, String> = emptyMap<CurrencyData, String>()
)
