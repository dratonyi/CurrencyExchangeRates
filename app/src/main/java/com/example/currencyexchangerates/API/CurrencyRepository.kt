package com.example.currencyexchangerates.API

import android.icu.util.Currency
import android.util.Log
import com.example.currencyexchangerates.constants.Constants.Companion.API_KEY
import com.example.currencyexchangerates.data.AllCurrencies
import com.example.currencyexchangerates.data.ExchangeRatesResponse
import javax.inject.Inject

class CurrencyRepository(
    private val currencyApi: CurrencyApi
){
    suspend fun getAllCurrencies(): AllCurrencies {
        Log.d("CurrencyRepository", "Repository API call initiated")

        return try {
            val response = currencyApi.getAllCurrencies(API_KEY)

            if (response.isSuccessful) {
                Log.d("CurrencyRepository-getAllCurrencies", "API response: ${response.body()}")
                response.body() ?: AllCurrencies(success = false, symbols = emptyMap())
            } else {
                Log.e("CurrencyRepository-getAllCurrencies", "API request failed: ${response.errorBody()?.string()}")
                AllCurrencies(success = false, symbols = emptyMap())
            }
        } catch (e: Exception) {
            Log.e("CurrencyRepository-getAllCurrencies", "Error during API call: ${e.message}")
            AllCurrencies(success = false, symbols = emptyMap())
        }
    }

    suspend fun getExchangeRates(base: Currency, symbols: List<Currency>): ExchangeRatesResponse {
        var symbolsString = buildString {
            symbols.forEachIndexed {index, curr ->
                append(curr.currencyCode)
                if(index != symbols.lastIndex) {
                    append(",")
                }
            }
        }

        Log.d("CurrencyRepository-getExchangeRates", "symbols as a string: $symbolsString")

        return try {
            val response = currencyApi.getLatestRate(API_KEY, base.currencyCode, symbolsString)

            if(response.isSuccessful) {
                Log.d("CurrencyRepository-getExchangeRates", "LatestRates API request successful: ${response.body()}")
                response.body() ?: ExchangeRatesResponse(success = false, timestamp = null, base = null, date = null, rates = emptyMap())
            }
            else {
                Log.e("CurrencyRepository-getExchangeRates", "LatestRates API request failed: ${response.errorBody()?.string()}")
                ExchangeRatesResponse(success = false, timestamp = null, base = null, date = null, rates = emptyMap())
            }
        }
        catch (e: Exception) {
            Log.e("CurrencyRepository-getExchangeRates", "Error during API call: ${e.message}")
            ExchangeRatesResponse(success = false, timestamp = null, base = null, date = null, rates = emptyMap())
        }
    }
}