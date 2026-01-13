package com.priorDev.pokerroutejc.di

import com.priorDev.pokerroutejc.core.data.PokedexRepo
import com.priorDev.pokerroutejc.core.data.PokedexRepoImp
import com.priorDev.pokerroutejc.core.data.network.pokedex.PokedexApolloService
import com.priorDev.pokerroutejc.core.data.network.pokedex.PokedexApolloServicesImp
import com.priorDev.pokerroutejc.features.pokedex.presentation.PokedexViewModel
import com.priorDev.pokerroutejc.features.pokedex_selection.presentation.VersionGroupViewModel
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