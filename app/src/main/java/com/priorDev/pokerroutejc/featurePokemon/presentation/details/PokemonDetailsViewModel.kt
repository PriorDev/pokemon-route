package com.priorDev.pokerroutejc.featurePokemon.presentation.details

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.core.Resource
import com.priorDev.pokerroutejc.core.UiMessages
import com.priorDev.pokerroutejc.core.routes.RoutesPokemon
import com.priorDev.pokerroutejc.featurePokemon.domain.AbilityDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonRepository
import com.priorDev.pokerroutejc.featurePokemon.domain.useCases.PokemonUseCases
import com.priorDev.pokerroutejc.featureTypes.domain.DamageRelationsData
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
) : ViewModel() {
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
                repository.getPokemon(pokemonName).collect { resource ->
                    when (resource) {
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

    fun onEvent(event: PokemonDetailsEvents) {
        when (event) {
            PokemonDetailsEvents.OnDismiss -> onDismiss()
            is PokemonDetailsEvents.OnGenerationSelect -> onGenerationSelect(event.generation)
            PokemonDetailsEvents.OnToggleFilterVisibility -> onToggleFilterVisibility()
            is PokemonDetailsEvents.OnTypeSelect -> onTypeSelect(event.typeId)
            is PokemonDetailsEvents.OnAbilityClick -> onAbilityClick(event.ability)
            PokemonDetailsEvents.OnAbilityDismiss -> onAbilityDismiss()
            is PokemonDetailsEvents.OnSearchTextChange -> onSearchTextChanged(event.text)
        }
    }

    private fun onSearchTextChanged(text: String) {
        _states.value = states.value.copy(textSearch = text)
        filterMoves()
    }

    private fun onAbilityDismiss() {
        _states.value = states.value.copy(
            visibleAbilityDetails = null,
            isAbilityLoading = null
        )
    }

    private fun onAbilityClick(ability: String) {
        viewModelScope.launch {
            repository.getAbility(ability).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _states.value = states.value.copy(
                            visibleAbilityDetails = AbilityDetailsData(
                                effect = resource.uiMessages.toString()
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _states.value = states.value.copy(isAbilityLoading = resource.isLoading)
                    }
                    is Resource.Success -> {
                        _states.value = states.value.copy(visibleAbilityDetails = resource.data)
                    }
                }
            }
        }
    }

    fun onDismiss() {
        _commonStates.value = commonStates.value.copy(uiMessages = null)
    }

    private fun onGenerationSelect(generation: String) {
        _states.value = states.value.copy(
            selectedGeneration = generation
        )

        filterMoves()
    }

    private fun onTypeSelect(typeId: Int) {
        _states.value = states.value.copy(
            selectedTypeId = typeId
        )

        filterMoves()
    }

    private fun onToggleFilterVisibility() {
        _states.value = states.value.let { states ->
            states.copy(
                isFiltersExpanded = !states.isFiltersExpanded
            )
        }
    }

    private fun filterMoves() {
        _moves.forEach { move ->
            val selectedGeneration = states.value.selectedGeneration.lowercase()
            val isGenerationMatch = selectedGeneration.isEmpty()
                .or(move.generationName.lowercase() == selectedGeneration)

            val selectedType = states.value.selectedTypeId
            val isTypeMatch = selectedType == 0 || selectedType == (move.type?.id ?: 0)

            val isTextSearchMatch = if (states.value.textSearch == "") {
                true
            } else {
                move.name.contains(states.value.textSearch)
            }

            move.isVisible = isGenerationMatch && isTypeMatch && isTextSearchMatch
        }
    }

    private suspend fun getWeaknesses() {
        val resource = useCases.getWeaknessesAndStrengths(
            states.value.pokemon.types
        )

        when (resource) {
            is Resource.Error -> showErrorMessage(resource.uiMessages)
            is Resource.Loading -> handleLoadingWheel(resource.isLoading)
            is Resource.Success -> {
                _states.value = states.value.copy(
                    weaknessesAndStrengths = resource.data ?: DamageRelationsData()
                )
            }
        }
    }

    private suspend fun getMoves() {
        _moves.clear()
        repository.getMoveDetails(states.value.pokemon.moves).collect { resource ->
            when (resource) {
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

    private fun showErrorMessage(uiMessages: UiMessages?) {
        _commonStates.value = commonStates.value.copy(uiMessages = uiMessages)
    }

    private fun handleLoadingWheel(isLoading: Boolean) {
        _commonStates.value = commonStates.value.copy(isLoading = isLoading)
    }
}
