package com.example.currencyexchangerates.data

import com.google.gson.annotations.SerializedName

data class AllCurrencies(
    @SerializedName("success") val success: Boolean,
    @SerializedName("symbols") val symbols: Map<String, String>
)