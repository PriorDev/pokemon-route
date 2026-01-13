package com.priorDev.pokerroutejc.features.pokemon_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priorDev.pokerroutejc.core.utils.ResourceFlow
import com.priorDev.pokerroutejc.core.data.network.pokemon.PokemonApolloService
import com.priorDev.pokerroutejc.core.utils.GlobalEventChannel

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PkSearchViewModel(
    private val pokemonApolloService: PokemonApolloService,
    private val globalEventChannel: GlobalEventChannel
) : ViewModel() {
    private val _states = MutableStateFlow(PkSearchState())
    val states = _states.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: PkSearchEvent) {
        when (event) {
            is PkSearchEvent.OnSearch -> {
                _states.update { it.copy(searchText = event.query) }
                searchPokemonByName(event.query.lowercase())
            }

            PkSearchEvent.OnNavigateUp -> globalEventChannel.navigateUp()

            is PkSearchEvent.OnNavigate -> globalEventChannel.navigate(event.route, event.navOptions)
        }
    }

    private fun searchPokemonByName(name: String) {
        viewModelScope.launch {
            searchJob?.cancel()

            searchJob = viewModelScope.launch {
                delay(500L)

                if (name.isBlank()) {
                    _states.update { it.copy(pokemonNames = emptyList()) }
                } else {
                    when (val result = pokemonApolloService.getPokemonByName(name)) {
                        is ResourceFlow.Error -> {
                            // Do nothing
                        }
                        is ResourceFlow.Success -> {
                            _states.update { it.copy(pokemonNames = result.data.orEmpty()) }
                        }
                        is ResourceFlow.Loading -> {
                            // Do nothing
                        }
                    }
                }
            }
        }
    }
}
