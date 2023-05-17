package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

import android.util.Log
import androidx.lifecycle.*
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.core.handleResource
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonRepository
import com.prior_dev.pokerroutejc.feature_pokemon.domain.use_cases.GetWeaknessesAndStrengths
import com.prior_dev.pokerroutejc.feature_pokemon.domain.use_cases.PokemonUseCases
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.RoutesPokemon
import com.prior_dev.pokerroutejc.feature_types.domain.DamageRelationsData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val savedStateHandle: SavedStateHandle,
    private val useCases: PokemonUseCases
): ViewModel(){
    private val _states = MutableLiveData(CommonStates())
    val states: LiveData<CommonStates> = _states

    private val _pokemon = MutableLiveData(PokemonData())
    val pokemon: LiveData<PokemonData> = _pokemon

    private val _weaknessesAndStrengths = MutableLiveData(DamageRelationsData())
    val weaknessesAndStrengths: LiveData<DamageRelationsData> = _weaknessesAndStrengths

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

                    val weaknessesAndStrengthsResource = useCases.getWeaknessesAndStrengths(
                        pokemon.value!!.types
                    )

                    handleResource(
                        result = weaknessesAndStrengthsResource,
                        _states = _states,
                        states = states
                    ){
                        _weaknessesAndStrengths.value = weaknessesAndStrengthsResource.data
                    }

            }
        }

    }

    fun onDismiss(){
        _states.value = states.value?.copy(message = "")
    }
}