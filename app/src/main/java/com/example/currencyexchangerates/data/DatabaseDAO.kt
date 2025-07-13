package com.example.currencyexchangerates.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDAO {

    //SavedData

    @Upsert
    suspend fun saveData(data: SavedData)

    @Delete
    suspend fun deleteSavedData(data: SavedData)

    @Query("SELECT * FROM last_saved_data WHERE id = 0")
    suspend fun getSavedData(): SavedData

    //Symbols

    @Query("SELECT * FROM symbols WHERE id = 0")
    suspend fun getSavedSymbols(): Symbols?

    @Upsert
    suspend fun saveSymbols(symbols: Symbols)

    @Delete
    suspend fun deleteSymbols(symbols: Symbols)
}