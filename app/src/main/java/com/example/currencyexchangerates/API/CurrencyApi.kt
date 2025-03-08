package com.example.currencyexchangerates.API

import com.example.currencyexchangerates.data.AllCurrencies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("v1/symbols")
    suspend fun getAllCurrencies(@Query("access_key") key: String): Response<AllCurrencies>
}