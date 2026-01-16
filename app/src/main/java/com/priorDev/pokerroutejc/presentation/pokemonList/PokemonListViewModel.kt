package com.priorDev.pokerroutejc.presentation.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonApolloService
import com.priorDev.pokerroutejc.domain.pokemon.models.toDomain
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.ui.Routes
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonListViewModel(
    pager: Pager<Int, PokemonNameEntity>,
    private val globalEventChannel: GlobalEventChannel,
    private val pokemonApolloService: PokemonApolloService
) : ViewModel() {
    private val _states = MutableStateFlow(PokemonListState())
    val states = _states.asStateFlow()

    private var searchJob: Job? = null

    val pokemonList = pager
        .flow
        .map { pagingData ->
            _states.update { it.copy(isLoading = false, isRefreshing = false) }
            pagingData.map { pokemon ->
                pokemon.toDomain()
            }
        }
        .cachedIn(viewModelScope)

    fun onEvent(event: PokemonListEvent) {
        when (event) {
            PokemonListEvent.OnDismiss -> { /* no-op */ }

            PokemonListEvent.OnRefresh -> onRefresh()

            is PokemonListEvent.OnSearchQueryChange -> {
                _states.update { it.copy(searchText = event.query) }
                searchPokemonByName(event.query.lowercase())
            }

            is PokemonListEvent.Navigate -> {
                globalEventChannel.navigate(event.route, event.navOptions)
            }
        }
    }

    private fun searchPokemonByName(name: String) {
        viewModelScope.launch {
            searchJob?.cancel()

            searchJob = viewModelScope.launch {
                delay(500L)

                if (name.isBlank()) {
                    _states.update { it.copy(searchResults = emptyList()) }
                } else {
                    when (val result = pokemonApolloService.getPokemonByName(name)) {
                        is ResourceFlow.Error -> {
                            // Do nothing
                        }
                        is ResourceFlow.Success -> {
                            _states.update { it.copy(searchResults = result.data.orEmpty()) }
                        }
                        is ResourceFlow.Loading -> {
                            // Do nothing
                        }
                    }
                }
            }
        }
    }

    private fun onRefresh() {
        _states.update { it.copy(isRefreshing = true) }
    }
}
