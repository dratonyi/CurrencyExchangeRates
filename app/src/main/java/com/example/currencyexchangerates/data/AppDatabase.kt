package com.example.currencyexchangerates.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SavedData::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract val dao: DatabaseDAO
}