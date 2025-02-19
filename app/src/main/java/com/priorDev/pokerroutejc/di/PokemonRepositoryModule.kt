package com.priorDev.pokerroutejc.di

import com.priorDev.pokerroutejc.data.PokemonRepoImp
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonNetService
import com.priorDev.pokerroutejc.data.PokemonRepo
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonApolloServiceImp
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonApolloService
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonNetServiceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PokemonRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun providerPokemonRepository(
        pokemonRepository: PokemonRepoImp
    ): PokemonRepo

    @Binds
    @ViewModelScoped
    abstract fun providerPokemonNameClient(
        pokemonNameClient: PokemonApolloServiceImp
    ): PokemonApolloService

    @Binds
    @ViewModelScoped
    abstract fun provicesPokemonService(service: PokemonNetServiceImp): PokemonNetService
}
