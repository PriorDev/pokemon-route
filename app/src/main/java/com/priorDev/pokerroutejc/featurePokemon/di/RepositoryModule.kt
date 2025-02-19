package com.priorDev.pokerroutejc.featurePokemon.di

import com.priorDev.pokerroutejc.featurePokemon.data.PokemonRepositoryImp
import com.priorDev.pokerroutejc.featurePokemon.data.PokemonService
import com.priorDev.pokerroutejc.featurePokemon.data.network.PokemonServiceImp
import com.priorDev.pokerroutejc.featurePokemon.data.PokemonRepository
import com.priorDev.pokerroutejc.featurePokemon.data.network.ApolloPokemonClient
import com.priorDev.pokerroutejc.featurePokemon.data.network.IPokemonNameClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun providerPokemonRepository(
        pokemonRepository: PokemonRepositoryImp
    ): PokemonRepository

    @Binds
    @ViewModelScoped
    abstract fun providerPokemonNameClient(
        pokemonNameClient: ApolloPokemonClient
    ): IPokemonNameClient

    @Binds
    @ViewModelScoped
    abstract fun provicesPokemonService(service: PokemonServiceImp): PokemonService
}
