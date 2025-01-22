package com.example.currencyexchangerates.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String
)

@Entity(tableName = "last_saved_data")
data class SavedData(
    @PrimaryKey val id: Int = 0,
    val baseCurrency: String,
    val targetCurrency: String,
    val baseAmount: String
)