package com.priorDev.pokerroutejc.presentation.pokemonSearch

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonApolloService
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonNameData
import com.priorDev.pokerroutejc.utils.IGlobalEventChannel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PkSearchViewModel @Inject constructor(
    private val pokemonNameClient: PokemonApolloService,
    private val globalEventChannel: IGlobalEventChannel
) : ViewModel() {
    private val _pokemonNames = mutableStateListOf<PokemonNameData>()
    val pokemonNames: List<PokemonNameData> = _pokemonNames

    private var searchJob: Job? = null

    fun onEvent(event: PkSearchEvent) {
        when (event) {
            is PkSearchEvent.OnSearch -> searchPokemonByName(event.query.lowercase())

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
                    _pokemonNames.clear()
                } else {
                    when (val result = pokemonNameClient.getPokemonByName(name)) {
                        is ResourceFlow.Error -> {
                            // Do nothing
                        }
                        is ResourceFlow.Success -> {
                            _pokemonNames.clear()
                            _pokemonNames.addAll(result.data.orEmpty())
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
