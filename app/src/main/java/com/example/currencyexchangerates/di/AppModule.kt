package com.example.currencyexchangerates.di

import android.content.Context
import androidx.room.Room
import com.example.currencyexchangerates.API.CurrencyApi
import com.example.currencyexchangerates.API.CurrencyRepository
import com.example.currencyexchangerates.data.AppDatabase
import com.example.currencyexchangerates.data.DatabaseDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database.db").build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): DatabaseDAO {
        return appDatabase.dao
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(currencyApi: CurrencyApi): CurrencyRepository {
        return CurrencyRepository(currencyApi)
    }
}