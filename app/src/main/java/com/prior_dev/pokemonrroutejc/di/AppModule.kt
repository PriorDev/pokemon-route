package com.prior_dev.pokemonrroutejc.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.prior_dev.pokemonrroutejc.data.database.MyDataBase
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
    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MyDataBase::class.java, MyDataBase.DATABASE_NAME).build()
}