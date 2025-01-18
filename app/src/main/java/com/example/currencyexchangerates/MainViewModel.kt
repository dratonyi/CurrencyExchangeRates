package com.example.currencyexchangerates

import android.icu.util.Currency
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _baseCurrency = MutableStateFlow(Currency.getInstance("HUF"))
    val baseCurrency = _baseCurrency.asStateFlow()
    private val _baseAmount = MutableStateFlow("100.21")
    val baseAmount = _baseAmount.asStateFlow()

    private val _targetCurrency = MutableStateFlow(Currency.getInstance("CAD"))
    val targetCurrency = _targetCurrency.asStateFlow()
    private val _targetAmount = MutableStateFlow("100.21")
    val targetAmount = _targetAmount.asStateFlow()

    init {

    }

    private fun formatCurrency(amount: Double, currency: Currency): String {
        val fractionDigits = currency.defaultFractionDigits
        return "%.${fractionDigits}f".format(amount)
    }

    fun updateAmount(newAmount: Double, code: String) {
        when(code) {
            _baseCurrency.value.currencyCode -> {_baseAmount.value = formatCurrency(newAmount, _baseCurrency.value)}
            _targetCurrency.value.currencyCode -> {_targetAmount.value = formatCurrency(newAmount, _targetCurrency.value)}
        }

        //once a conversion rate is available also update the value of target/base currency here
    }
}