package com.example.currencyexchangerates.API

import com.example.currencyexchangerates.data.AllCurrencies
import com.example.currencyexchangerates.data.ExchangeRatesResponse
import okhttp3.internal.connection.Exchange
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("v1/symbols")
    suspend fun getAllCurrencies(@Query("access_key") key: String): Response<AllCurrencies>

    @GET("v1/latest")
    suspend fun getLatestRate(@Query("access_key") key: String, @Query("base") base: String, @Query("symbols") symbols: String): Response<ExchangeRatesResponse>
}