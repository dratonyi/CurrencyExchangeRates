package com.example.currencyexchangerates

import android.icu.util.Currency
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _baseCurrency = MutableStateFlow(Currency.getInstance("EUR"))
    val baseCurrency = _baseCurrency.asStateFlow()
    private val _baseAmount = MutableStateFlow("100.21")
    val baseAmount = _baseAmount.asStateFlow()

    private val _targetCurrency = MutableStateFlow(Currency.getInstance("CAD"))
    val targetCurrency = _targetCurrency.asStateFlow()
    private val _targetAmount = MutableStateFlow("100.21")
    val targetAmount = _targetAmount.asStateFlow()

    val exchangeRate: Double = 1.5

    init {

    }

    private fun formatCurrency(amount: Double, currency: Currency): String {
        //val fractionDigits = currency.defaultFractionDigits
        //return "%.${fractionDigits}f".format(amount)
        return amount.toString()
    }

    /*fun updateAmount(newAmount: Double, code: String) {
        when(code) {
            _baseCurrency.value.currencyCode -> {_baseAmount.value = formatCurrency(newAmount, _baseCurrency.value)}
            _targetCurrency.value.currencyCode -> {_targetAmount.value = formatCurrency(newAmount, _targetCurrency.value)}
        }

        //once a conversion rate is available also update the value of target/base currency here
    }*/

    fun updateBaseAmount(newAmount: String) {
        _baseAmount.value = newAmount
        if(newAmount.isNotBlank()) {
            _targetAmount.value = (_baseAmount.value.toDouble() * exchangeRate).toString()
        }
        else {
            _targetAmount.value = ""
        }
    }

    fun updateTargetAmount(newAmount: String) {
        _targetAmount.value = newAmount
        try {
            if(newAmount.isNotBlank()) {
                _baseAmount.value = (_targetAmount.value.toDouble() / exchangeRate).toString()
            }
            else {
                _baseAmount.value = ""
            }
        } catch (e: ArithmeticException) {
            _baseAmount.value = "0"
        }
    }
}