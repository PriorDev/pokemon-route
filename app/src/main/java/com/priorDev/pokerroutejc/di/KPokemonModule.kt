package com.priorDev.pokerroutejc.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.priorDev.pokerroutejc.data.PokemonRepo
import com.priorDev.pokerroutejc.data.PokemonRepoImp
import com.priorDev.pokerroutejc.data.database.MyDataBase
import com.priorDev.pokerroutejc.data.database.PokemonDao
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonApolloService
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonApolloServiceImp
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonNetService
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonNetServiceImp
import com.priorDev.pokerroutejc.domain.pokemon.PokemonNameRemoteMediator
import com.priorDev.pokerroutejc.domain.pokemon.PokemonNameRemoteMediator.Companion.PAGE_SIZE
import com.priorDev.pokerroutejc.domain.pokemon.useCases.GetDamageRelations
import com.priorDev.pokerroutejc.domain.pokemon.useCases.PokemonUseCases
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsViewModel
import com.priorDev.pokerroutejc.presentation.pokemonList.PokemonListViewModel
import com.priorDev.pokerroutejc.presentation.pokemonSearch.PkSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val kPokemonModule = module {
    single {
        get<MyDataBase>().pokemonDao
    }

    factory {
        GetDamageRelations(
            typeRepo = get()
        )
    }

    factory<PokemonUseCases> {
        PokemonUseCases(
            getDamageRelations = get()
        )
    }

    single<PokemonNetService> {
        PokemonNetServiceImp(
            networkService = get()
        )
    }

    single<PokemonApolloService> {
        PokemonApolloServiceImp(
            apolloCaller = get()
        )
    }

    single<PokemonRepo> {
        PokemonRepoImp(
            service = get(),
            graphQLClient = get()
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    single {
        val pokemonDao: PokemonDao = get()

        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
            ),
            remoteMediator = PokemonNameRemoteMediator(
                pokemonNetService = get(),
                pokemonDao = get(),
            ),
            pagingSourceFactory = {
                pokemonDao.pagingSource()
            }
        )
    }

    viewModel {
        PokemonListViewModel(
            pager = get(),
            globalEventChannel = get()
        )
    }

    viewModel {
        PokemonDetailsViewModel(
            repository = get(),
            savedStateHandle = get(),
            useCases = get(),
            globalEvent = get(),
            settingsRepo = get()
        )
    }

    viewModel {
        PkSearchViewModel(
            pokemonApolloService = get(),
            globalEventChannel = get()
        )
    }
}
