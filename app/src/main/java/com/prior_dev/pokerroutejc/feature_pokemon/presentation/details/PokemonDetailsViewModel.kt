package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.core.handleResource
import com.prior_dev.pokerroutejc.feature_pokemon.domain.MoveDetailsData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonRepository
import com.prior_dev.pokerroutejc.feature_pokemon.domain.use_cases.PokemonUseCases
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.RoutesPokemon
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.utils.MoveViewStates
import com.prior_dev.pokerroutejc.feature_types.domain.DamageRelationsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private val _moves = mutableStateListOf<MoveDetailsData>()
    val moves: List<MoveDetailsData> = _moves
    private val _movesStates = MutableLiveData(MoveViewStates())
    val moveStates: LiveData<MoveViewStates> = _movesStates

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

                    launch {
                        getWeaknesses()
                    }

                    launch {
                        getMoves()
                    }
            }
        }

    }

    fun onDismiss(){
        _states.value = states.value?.copy(message = "")
    }

    private suspend fun getWeaknesses(){
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

    private suspend fun getMoves(){
        _moves.clear()
        repository.getMoveDetails(pokemon.value!!.moves).collect{ result ->
            when(result){
                is Resource.Error -> {
                    _movesStates.value = moveStates.value?.copy(
                        message = result.message ?: ""
                    )
                }
                is Resource.Loading -> {
                    _movesStates.value = moveStates.value?.copy(
                        isLoading = result.isLoading
                    )
                }
                is Resource.Success -> {
                    result.data?.let { move ->
                        _moves.add(move)
                    }
                }
            }
        }
    }

    fun onTypeSelect(typeId: Int){
        _movesStates.value = moveStates.value?.copy(
            selectedTypeId = typeId
        )

        filterMoves()
    }

    fun onGenerationSelect(generation: String){
        _movesStates.value = moveStates.value?.copy(
            selectedGeneration = generation
        )

        filterMoves()
    }

    private fun filterMoves(){
        _moves.forEach{ move ->
            val selectedGeneration = moveStates.value?.selectedGeneration?.lowercase() ?: ""
            val isGenerationMatch = selectedGeneration == "" ||
                    move.generationName.lowercase() == selectedGeneration

            val selectedType = moveStates.value?.selectedTypeId ?: 0
            val isTypeMatch = selectedType == 0 || selectedType == (move.type?.id ?: 0)


            move.isVisible = isGenerationMatch && isTypeMatch
        }
    }

    fun onToggleFilterVisibility(){
        _movesStates.value = moveStates.value?.let { states ->
            states.copy(
                isFiltersExpanded = !states.isFiltersExpanded
            )
        }
    }
}