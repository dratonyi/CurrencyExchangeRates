package com.example.currencyexchangerates

import android.icu.util.Currency
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.currencyexchangerates.data.CurrencyData
import com.example.currencyexchangerates.data.DatabaseDAO
import com.example.currencyexchangerates.data.SavedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: DatabaseDAO
) : ViewModel() {
    private val _baseCurrency = MutableStateFlow(CurrencyData(Currency.getInstance("EUR"), "100.21"))
    val baseCurrency = _baseCurrency.asStateFlow()

    private val _targetCurrency = MutableStateFlow(CurrencyData(Currency.getInstance("CAD"), "100.21"))
    val targetCurrency = _targetCurrency.asStateFlow()

    val exchangeRate: Double = 1.5

    var listOfCurrencies: MutableList<Currency> = Currency.getAvailableCurrencies().toMutableList()

    private val _currSearch = MutableStateFlow("")
    val currSearch = _currSearch.asStateFlow()

    init {
        getSavedData()
    }

    fun onEvent(event: UserEvent) {
        when(event) {
            is UserEvent.ChangeBaseAmount -> {
                updateBaseAmount(event.newAmount)
                saveData()
            }
            UserEvent.ChangeBaseCurrency -> TODO()
            is UserEvent.ChangeTargetAmount -> {
                updateTargetAmount(event.newAmount)
                saveData()
            }
            UserEvent.ChangeTargetCurrency -> TODO()
            is UserEvent.SearchCurrency-> {
                searchCurrencyList(event.search)
            }
            UserEvent.doNothing -> TODO()
        }
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

    private fun updateBaseAmount(newAmount: String) {
        _baseCurrency.update { it.copy(
            amount = newAmount
        ) }
        if(newAmount.isNotBlank()) {
            _targetCurrency.update { it.copy(
                amount = (_baseCurrency.value.amount.toDouble() * exchangeRate).toString()
            ) }
        }
        else {
            _targetCurrency.update { it.copy(
                amount = ""
            ) }
        }
    }

    private fun updateTargetAmount(newAmount: String) {
        _targetCurrency.update { it.copy(
            amount = newAmount
        ) }
        try {
            if(newAmount.isNotBlank()) {
                _baseCurrency.update { it.copy(
                    amount = (_targetCurrency.value.amount.toDouble() / exchangeRate).toString()
                ) }
            }
            else {
                _baseCurrency.update { it.copy(
                    amount = ""
                ) }
            }
        } catch (e: ArithmeticException) {
            _baseCurrency.update { it.copy(
                amount = "0"
            ) }
        }
    }

    private fun searchCurrencyList(search: String) {
        _currSearch.update { search }

        if(_currSearch.value == "") {
            listOfCurrencies = Currency.getAvailableCurrencies().toMutableList()
        }
        else {
            listOfCurrencies = (listOfCurrencies.filter { currency ->
                currency.currencyCode.contains(_currSearch.value) || currency.displayName.contains(_currSearch.value)
            }).toMutableList()
        }
    }

    private fun saveData() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                dao.saveData(
                    SavedData(
                        baseCurrency = _baseCurrency.value.currency.currencyCode,
                        targetCurrency = _targetCurrency.value.currency.currencyCode,
                        baseAmount = _baseCurrency.value.amount
                    )
                )
                Log.d("MainViewModel", "Saved ${_baseCurrency.value.currency.currencyCode}, ${_targetCurrency.value.currency.currencyCode}, ${_baseCurrency.value.amount} in database")
            }
            catch (e: Exception) {
                e.printStackTrace()
                Log.d("MainViewModel", "Error saving to database")
            }
        }
    }

    private fun getSavedData() {
        CoroutineScope(Dispatchers.Default).launch {
            val retrievedData = dao.getSavedData()
            Log.d("MainViewModel", "Retrieved ${retrievedData.baseCurrency}, ${retrievedData.targetCurrency}, ${retrievedData.baseAmount} from database")

            try {
                _baseCurrency.update {
                    it.copy(
                        currency = Currency.getInstance(retrievedData.baseCurrency)
                    )
                }
                _baseCurrency.update {
                    it.copy(
                        currency = Currency.getInstance(retrievedData.targetCurrency)
                    )
                }

                updateBaseAmount(retrievedData.baseAmount)
            }
            catch (e: Exception) {
                //set default amounts
                Log.d("MainViewModel", "Error retrieving from database")
            }
        }
    }
}