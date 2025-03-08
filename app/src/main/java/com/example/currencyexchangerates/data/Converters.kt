package com.example.currencyexchangerates.data

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.util.Date

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromMap(value: Map<String, String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMap(value: String): Map<String, String> {
        val type = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromDate(value: Date): Long {
        return value.time
    }

    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }
}