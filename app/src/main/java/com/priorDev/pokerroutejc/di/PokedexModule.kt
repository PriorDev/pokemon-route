package com.priorDev.pokerroutejc.di

import com.priorDev.pokerroutejc.data.PokedexRepo
import com.priorDev.pokerroutejc.data.PokedexRepoImp
import com.priorDev.pokerroutejc.data.network.pokedex.PokedexApolloService
import com.priorDev.pokerroutejc.data.network.pokedex.PokedexApolloServicesImp
import com.priorDev.pokerroutejc.presentation.pokedex.PokedexViewModel
import com.priorDev.pokerroutejc.presentation.versionGroups.VersionGroupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PokedexModule = module {
    single<PokedexApolloService> {
        PokedexApolloServicesImp(
            apolloCaller = get()
        )
    }

    single<PokedexRepo> {
        PokedexRepoImp(
            pokedexApolloService = get(),
            dataStore = get()
        )
    }

    viewModel {
        VersionGroupViewModel(
            pokedexRepo = get(),
            eventChannel = get()
        )
    }

    viewModel {
        PokedexViewModel(
            pokedexRepo = get(),
            savedStateHandle = get(),
            eventChannel = get()
        )
    }
}