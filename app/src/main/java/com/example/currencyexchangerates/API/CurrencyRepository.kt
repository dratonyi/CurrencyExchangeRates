package com.example.currencyexchangerates.API

import android.util.Log
import com.example.currencyexchangerates.constants.Constants.Companion.API_KEY
import com.example.currencyexchangerates.data.AllCurrencies
import javax.inject.Inject

class CurrencyRepository(
    private val currencyApi: CurrencyApi
){
    suspend fun getAllCurrencies(): AllCurrencies {
        Log.d("CurrencyRepository", "Repository API call initiated")

        return try {
            val response = currencyApi.getAllCurrencies(API_KEY)

            if (response.isSuccessful) {
                Log.d("CurrencyRepository", "API response: ${response.body()}")
                response.body() ?: AllCurrencies(success = false, symbols = emptyMap()) // Handle null body
            } else {
                Log.e("CurrencyRepository", "API request failed: ${response.errorBody()?.string()}")
                AllCurrencies(success = false, symbols = emptyMap()) // Return default object on failure
            }
        } catch (e: Exception) {
            Log.e("CurrencyRepository", "Error during API call: ${e.message}")
            AllCurrencies(success = false, symbols = emptyMap()) // Handle network errors
        }
    }
}