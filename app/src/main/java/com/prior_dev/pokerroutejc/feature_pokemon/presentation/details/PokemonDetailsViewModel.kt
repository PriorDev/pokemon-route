package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.core.UiMessages
import com.prior_dev.pokerroutejc.feature_pokemon.domain.MoveDetailsData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonRepository
import com.prior_dev.pokerroutejc.feature_pokemon.domain.use_cases.PokemonUseCases
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon
import com.prior_dev.pokerroutejc.feature_types.domain.DamageRelationsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
    savedStateHandle: SavedStateHandle,
    private val useCases: PokemonUseCases
): ViewModel(){
    private val _commonStates = MutableStateFlow(CommonStates())
    val commonStates = _commonStates.asStateFlow()

    private val _states = MutableStateFlow(PokemonDetailsStates())
    val states = _states.asStateFlow()

    private val _moves = mutableStateListOf<MoveDetailsData>()
    val moves: List<MoveDetailsData> = _moves

    private val pokemonName = savedStateHandle.get<String>(RoutesPokemon.PokemonDetails.argPokemonName)

    init {
        viewModelScope.launch {
            pokemonName?.let {
                repository.getPokemon(pokemonName).collect{ resource ->
                    when(resource){
                        is Resource.Error -> showErrorMessage(resource.uiMessages)
                        is Resource.Loading -> handleLoadingWheel(resource.isLoading)
                        is Resource.Success -> {
                            _states.value = states.value.copy(pokemon = resource.data!!)
                        }
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

    fun onEvent(event: PokemonDetailsEvents){
        when(event){
            PokemonDetailsEvents.onDismiss -> onDismiss()
            is PokemonDetailsEvents.onGenerationSelect -> onGenerationSelect(event.generation)
            PokemonDetailsEvents.onToggleFilterVisibility -> onToggleFilterVisibility()
            is PokemonDetailsEvents.onTypeSelect -> onTypeSelect(event.typeId)
        }
    }

    fun onDismiss(){
        _commonStates.value = commonStates.value.copy(uiMessages = null)
    }

    private fun onGenerationSelect(generation: String){
        _states.value = states.value.copy(
            selectedGeneration = generation
        )

        filterMoves()
    }

    private fun onTypeSelect(typeId: Int){
        _states.value = states.value.copy(
            selectedTypeId = typeId
        )

        filterMoves()
    }

    private fun onToggleFilterVisibility(){
        _states.value = states.value.let { states ->
            states.copy(
                isFiltersExpanded = !states.isFiltersExpanded
            )
        }
    }

    private fun filterMoves(){
        _moves.forEach{ move ->
            val selectedGeneration = states.value.selectedGeneration.lowercase()
            val isGenerationMatch = selectedGeneration == "" ||
                    move.generationName.lowercase() == selectedGeneration

            val selectedType = states.value.selectedTypeId
            val isTypeMatch = selectedType == 0 || selectedType == (move.type?.id ?: 0)

            move.isVisible = isGenerationMatch && isTypeMatch
        }
    }

    private suspend fun getWeaknesses(){
        val resource = useCases.getWeaknessesAndStrengths(
            states.value.pokemon.types
        )

        when(resource){
            is Resource.Error -> showErrorMessage(resource.uiMessages)
            is Resource.Loading -> handleLoadingWheel(resource.isLoading)
            is Resource.Success -> {
                _states.value = states.value.copy(
                    weaknessesAndStrengths = resource.data ?: DamageRelationsData()
                )
            }
        }
    }

    private suspend fun getMoves(){
        _moves.clear()
        repository.getMoveDetails(states.value.pokemon.moves).collect{ resource ->
            when(resource){
                is Resource.Error -> showErrorMessage(resource.uiMessages)
                is Resource.Loading -> {
                    _states.value = states.value.copy(isLoading = resource.isLoading)
                }
                is Resource.Success -> {
                    resource.data?.let { move ->
                        _moves.add(move)
                    }
                }
            }
        }
    }

    private fun showErrorMessage(uiMessages: UiMessages?){
        _commonStates.value = commonStates.value.copy(uiMessages = uiMessages)
    }

    private fun handleLoadingWheel(isLoading: Boolean){
        _commonStates.value = commonStates.value.copy(isLoading = isLoading)
    }
}