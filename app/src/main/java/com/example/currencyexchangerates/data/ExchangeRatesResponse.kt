package com.example.currencyexchangerates.data

import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("timestamp") val timestamp: Double?,
    @SerializedName("base") val base: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("rates") val rates: Map<String, Double>
)
