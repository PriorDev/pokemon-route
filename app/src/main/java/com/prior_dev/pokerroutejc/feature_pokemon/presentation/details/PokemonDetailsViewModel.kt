package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

import androidx.lifecycle.*
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.handleResource
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonRepository
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.RoutesPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){
    private val _states = MutableLiveData(CommonStates())
    val states: LiveData<CommonStates> = _states

    private val _pokemon = MutableLiveData(PokemonData())
    val pokemon: LiveData<PokemonData> = _pokemon

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(RoutesPokemon.PokemonDetails.argPokemonName)
                ?.let { pokemonName ->
                    repository.getPokemon(pokemonName).collect{ result ->
                        handleResource(
                            result = result,
                            _states = _states,
                            states = states
                        ){
                            _pokemon.value = result.data
                        }
                    }
            }
        }
    }

    fun onDismiss(){
        _states.value = states.value?.copy(message = "")
    }
}