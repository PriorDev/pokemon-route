package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.Resource
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.data.PokemonRepo
import com.priorDev.pokerroutejc.domain.pokemon.useCases.PokemonUseCases
import com.priorDev.pokerroutejc.domain.types.models.DamageRelationsData
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.MoveFilterModel
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.utils.orZero

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val repository: PokemonRepo,
    savedStateHandle: SavedStateHandle,
    private val useCases: PokemonUseCases,
    private val globalEvent: GlobalEventChannel
) : ViewModel() {
    private val _commonStates = MutableStateFlow(CommonStates())
    val commonStates = _commonStates.asStateFlow()

    private val _states = MutableStateFlow(PokemonDetailsStates())
    val states = _states.asStateFlow()

    private val _moves = mutableStateMapOf<String, List<MoveDetailsData>>()
    val moves: Map<String, List<MoveDetailsData>> = _moves

    init {
        viewModelScope.launch {
            val navArg = savedStateHandle.toRoute<Routes.PkDetails>()
            repository.getPokemon(navArg.pokemonName).collect { resource ->
                when (resource) {
                    is ResourceFlow.Error -> showErrorMessage(resource.uiMessages)
                    is ResourceFlow.Loading -> handleLoadingWheel(resource.isLoading)
                    is ResourceFlow.Success -> {
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

            launch {
                getEvolutions()
            }
        }
    }

    private suspend fun getEvolutions() {
        when (val response = repository.getEvolutionChain(states.value.pokemon.id)) {
            is ResourceFlow.Error -> TODO()
            is ResourceFlow.Loading -> TODO()
            is ResourceFlow.Success -> {
                _states.update {
                    it.copy(evolutions = response.data.orEmpty())
                }
            }
        }
    }

    fun onEvent(event: PokemonDetailsEvents) {
        when (event) {
            PokemonDetailsEvents.OnDismiss -> onDismiss()
            is PokemonDetailsEvents.OnGenerationSelect -> onGenerationSelect(event.generation)
            is PokemonDetailsEvents.OnTypeCheck -> onTypeCheck(event.filter)
            is PokemonDetailsEvents.OnAbilityClick -> onAbilityClick(event.ability)
            PokemonDetailsEvents.OnAbilityDismiss -> onAbilityDismiss()
            is PokemonDetailsEvents.OnSearchTextChange -> onSearchTextChanged(event.text)
            is PokemonDetailsEvents.Navigate -> {
                globalEvent.navigate(event.route, event.navOptions)
            }
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
                    is ResourceFlow.Error -> {
                        _states.value = states.value.copy(
                            visibleAbilityDetails = AbilityDetailsData(
                                effect = resource.uiMessages.toString()
                            )
                        )
                    }
                    is ResourceFlow.Loading -> {
                        _states.value = states.value.copy(isAbilityLoading = resource.isLoading)
                    }
                    is ResourceFlow.Success -> {
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

    private fun onTypeCheck(filter: MoveFilterModel) {
        val updateFilter = _states.value.moveFilters.map {
            if (it.type.id == filter.type.id) {
                it.copy(checked = !it.checked)
            } else {
                it
            }
        }

        _states.update {
            it.copy(moveFilters = updateFilter)
        }

        if (updateFilter.any { it.checked }) {
            println("----|update move list")
            _moves
                .flatMap { it.value }
                .forEach { moveDetails ->
                    updateFilter
                        .firstOrNull { it.type.id == moveDetails.type?.id }
                        ?.let { filter ->
                            println("----|update move list ${moveDetails.name} value ${filter.checked}")
                            moveDetails.visible = filter.checked
                        }
                }
        } else {
            println("---|show all moves")
            _moves
                .flatMap { it.value }
                .forEach { moveDetails ->
                    moveDetails.visible = true
                }
        }
    }

    private fun filterMoves() {
//        _moves.forEach { move ->
//            val selectedGeneration = states.value.selectedGeneration.lowercase()
//            val isGenerationMatch = selectedGeneration.isEmpty()
//                .or(move.generationName.lowercase() == selectedGeneration)
//
//            val selectedType = states.value.selectedTypeId
//            val isTypeMatch = selectedType == 0 || selectedType == (move.type?.id ?: 0)
//
//            val isTextSearchMatch = if (states.value.textSearch == "") {
//                true
//            } else {
//                move.name.contains(states.value.textSearch)
//            }
//
//            move.isVisible = isGenerationMatch && isTypeMatch && isTextSearchMatch
//        }
    }

    private suspend fun getWeaknesses() {
        val resource = useCases.getWeaknessesAndStrengths(
            states.value.pokemon.types
        )

        when (resource) {
            is ResourceFlow.Error -> showErrorMessage(resource.uiMessages)
            is ResourceFlow.Loading -> handleLoadingWheel(resource.isLoading)
            is ResourceFlow.Success -> {
                _states.value = states.value.copy(
                    weaknessesAndStrengths = resource.data ?: DamageRelationsData()
                )
            }
        }
    }

    private suspend fun getMoves() {
        val response = repository.getPkMoves(
            pokemonId = states.value.pokemon.id,
            generationName = "generation-ix",
            language = "en"
        )

        when (response) {
            is Resource.Error -> TODO()
            is Resource.Success -> {
                _moves.putAll(response.data.orEmpty())

                val types = response.data
                    ?.asSequence()
                    ?.flatMap { it.value }
                    ?.mapNotNull { it.type }
                    ?.distinct()
                    ?.sortedBy { it.name }
                    ?.map {
                        MoveFilterModel(
                            checked = false,
                            type = it
                        )
                    }
                    .orEmpty()
                    .toList()

                _states.update {
                    it.copy(moveFilters = types)
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
