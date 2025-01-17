package com.example.currencyexchangerates

import android.icu.util.Currency
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _baseCurrency = MutableStateFlow(Currency.getInstance("EUR"))
    val baseCurrency = _baseCurrency.asStateFlow()
    private val _targetCurrency = MutableStateFlow(Currency.getInstance("CAD"))
    val targetCurrency = _targetCurrency.asStateFlow()

    init {

    }
}