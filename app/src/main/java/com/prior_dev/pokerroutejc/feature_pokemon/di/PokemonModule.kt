package com.prior_dev.pokerroutejc.feature_pokemon.di

import com.prior_dev.pokerroutejc.data.database.MyDataBase
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.PokemonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object PokemonModule {
    @ViewModelScoped
    @Provides
    fun providerPokemonApi(retrofit: Retrofit) = retrofit.create(PokemonApi::class.java)

    @ViewModelScoped
    @Provides
    fun providerPokemonDao(db: MyDataBase) = db.pokemonDao
}