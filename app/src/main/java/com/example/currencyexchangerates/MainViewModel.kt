package com.example.currencyexchangerates

import android.icu.util.Currency
import android.util.Log
import android.util.TimeUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangerates.API.CurrencyRepository
import com.example.currencyexchangerates.data.AllCurrencies
import com.example.currencyexchangerates.data.CurrencyData
import com.example.currencyexchangerates.data.CurrencyState
import com.example.currencyexchangerates.data.DatabaseDAO
import com.example.currencyexchangerates.data.SavedData
import com.example.currencyexchangerates.data.Symbols
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: DatabaseDAO,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _currencyState = MutableStateFlow(CurrencyState())
    val currencyState = _currencyState.asStateFlow()

    private val _baseCurrency = MutableStateFlow(CurrencyData(Currency.getInstance("EUR"), "100.21"))
    val baseCurrency = _baseCurrency.asStateFlow()

    private val _targetCurrency = MutableStateFlow(CurrencyData(Currency.getInstance("CAD"), "100.21"))
    val targetCurrency = _targetCurrency.asStateFlow()

    private var exchangeRate: Double = 0.0

    var listOfCurrencies: MutableList<Currency> = Currency.getAvailableCurrencies().toMutableList()

    private val _currSearch = MutableStateFlow("")
    val currSearch = _currSearch.asStateFlow()

    private val _allCurrencies = MutableStateFlow(mutableMapOf<String, String>())
    val allCurrencies = _allCurrencies.asStateFlow()

    var currCurrencyChangeCurrency = "EUR"
    var currCurrencyChangeType = "base"

    init {
        getSavedData()
    }

    private fun getAllCurrencies(){
        viewModelScope.launch {
            Log.d("MainViewModel", "Retrieving AllCurrencies")

            val retrievedData = dao.getSavedSymbols()

            if(retrievedData == null || retrievedData.symbols.isEmpty() || TimeUnit.MILLISECONDS.toMinutes(Date().time - retrievedData.dateUpdated.time) > 43800) {
                val temp = currencyRepository.getAllCurrencies()
                _allCurrencies.update { temp.symbols.toMutableMap() }
                dao.saveSymbols(Symbols(dateUpdated = Date(), symbols = _allCurrencies.value))
            }
            else {
                _allCurrencies.update { retrievedData.symbols.toMutableMap() }
            }
        }
    }

    fun onEvent(event: UserEvent) {
        when(event) {
            is UserEvent.ChangeBaseAmount -> {
                updateBaseAmount(event.newAmount)
                saveData()
            }
            is UserEvent.ChangeCurrency -> {
                if(currCurrencyChangeType == "base"){
                    updateCurrency(_baseCurrency, event.newCode)
                }
                else if(currCurrencyChangeType == "target") {
                    updateCurrency(_targetCurrency, event.newCode)
                }
            }
            is UserEvent.ChangeTargetAmount -> {
                updateTargetAmount(event.newAmount)
                saveData()
            }
            //is UserEvent.ChangeTargetCurrency -> TODO()
            is UserEvent.SearchCurrency-> {
                searchCurrencyList(event.search)
            }
            is UserEvent.GoToChangeCurrencyScreen -> {
                getAllCurrencies()
                currCurrencyChangeType = event.type
            }
            is UserEvent.swapCurrencies -> {
                swapCurrencies()
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
                        baseAmount = _baseCurrency.value.amount,
                        exchangeRate = exchangeRate
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
            try {
                val retrievedData = dao.getSavedData()

                Log.d("MainViewModel", "Retrieved ${retrievedData?.baseCurrency}, ${retrievedData?.targetCurrency}, ${retrievedData?.baseAmount} from database")

                _baseCurrency.update {
                    it.copy(
                        currency = Currency.getInstance(retrievedData?.baseCurrency)
                    )
                }
                _targetCurrency.update {
                    it.copy(
                        currency = Currency.getInstance(retrievedData?.targetCurrency)
                    )
                }

                if(retrievedData == null) {
                    Log.d("MainViewModel", "No Saved Exchange Rate Data, updating exchange rate")
                    updateExchangeRate()
                }

                exchangeRate = retrievedData?.exchangeRate ?: 0.0
                updateBaseAmount(retrievedData?.baseAmount ?: "0")
            }
            catch (e: Exception) {
                //set default amounts
                Log.d("MainViewModel", "Error retrieving from database")
            }
        }
    }

    private fun updateCurrency(currency: MutableStateFlow<CurrencyData>, newCode: String) {
        currency.update {
            it.copy(
                currency = Currency.getInstance(newCode)
            )
        }

        updateExchangeRate()
        saveData()
    }

    private fun updateExchangeRate() {
        viewModelScope.launch {
            val response = currencyRepository.getExchangeRates(_baseCurrency.value.currency, listOf(_targetCurrency.value.currency))

            if(response.success) {
                exchangeRate = response.rates[_targetCurrency.value.currency.currencyCode] ?: 1.0
                updateBaseAmount(_baseCurrency.value.amount)
                Log.d("MainViewModel-ExchangeRate", "Updated target value based on recieved exchange rate")
            }
        }
    }

    private fun swapCurrencies() {
        val temp = _baseCurrency.value
        _baseCurrency.update { _targetCurrency.value }
        _targetCurrency.update { temp }
        if(exchangeRate != 0.0) {
            exchangeRate = 1 / exchangeRate
        }
    }
}