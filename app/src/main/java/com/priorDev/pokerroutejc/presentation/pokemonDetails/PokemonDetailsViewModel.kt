package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.Resource
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.data.PokemonRepo
import com.priorDev.pokerroutejc.domain.pokemon.useCases.PokemonUseCases
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.retryFullScreen
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.MoveFilterModel
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PokemonMovesState
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.GlobalEventChannel

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
    private val _states = MutableStateFlow(PokemonDetailsStates())
    val states = _states.asStateFlow()

    private val _pkMovesStates = MutableStateFlow(PokemonMovesState())
    val pkMovesStates = _pkMovesStates.asStateFlow()

    private val _moves = mutableStateMapOf<String, List<MoveDetailsData>>()
    val moves: Map<String, List<MoveDetailsData>> = _moves

    init {
        viewModelScope.launch {
            val navArg = savedStateHandle.toRoute<Routes.PkDetails>()
            repository.getPokemon(navArg.pokemonName).collect { resource ->
                when (resource) {
                    is ResourceFlow.Error -> {

                    }
                    is ResourceFlow.Loading -> {

                    }
                    is ResourceFlow.Success -> {
                        _states.value = states.value.copy(pokemon = resource.data!!)
                    }
                }
            }

            launch {
                getDamageRelation()
            }

            launch {
                getPokemonMoves()
            }

            launch {
                getEvolutions()
            }
        }
    }

    private suspend fun getEvolutions() {
        when (val response = repository.getEvolutionChain(states.value.pokemon.id)) {
            is ResourceFlow.Error -> {

            }
            is ResourceFlow.Loading -> {

            }
            is ResourceFlow.Success -> {
                _states.update {
                    it.copy(evolutions = response.data.orEmpty())
                }
            }
        }
    }

    fun onEvent(event: PokemonDetailsEvents) {
        when (event) {
            PokemonDetailsEvents.OnDismiss -> {

            }
            is PokemonDetailsEvents.OnGenerationSelect -> {

            }
            is PokemonDetailsEvents.ToggleMoveFilterCheck -> toggleFilterMoveCheck(event.filter)
            is PokemonDetailsEvents.OnAbilityClick -> onAbilityClick(event.ability)
            PokemonDetailsEvents.OnAbilityDismiss -> onAbilityDismiss()
            is PokemonDetailsEvents.OnSearchTextChange -> onSearchTextChanged(event.text)
            is PokemonDetailsEvents.Navigate -> {
                globalEvent.navigate(event.route, event.navOptions)
            }

            is PokemonDetailsEvents.ToggleLearnMethodExpand -> toggleLearnMethodExpand(event)

            is PokemonDetailsEvents.SelectMove -> {
                _pkMovesStates.update { it.copy(selectedMove = event.move) }
            }
        }
    }

    private fun onSearchTextChanged(text: String) {
        _states.value = states.value.copy(textSearch = text)

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

    private suspend fun getDamageRelation() {
        val resource = useCases.getWeaknessesAndStrengths(
            states.value.pokemon.types
        )

        when (resource) {
            is ResourceFlow.Error -> TODO()
            is ResourceFlow.Loading -> TODO()
            is ResourceFlow.Success -> {
                resource.data?.let { damageRelation ->
                    _states.update { currentState ->
                        currentState.copy(damageRelations =  damageRelation)
                    }
                }
            }
        }
    }

    private fun getPokemonMoves() {
        viewModelScope.launch {
            _pkMovesStates.update {
                it.copy(loading = LoadingIndicator.SolidSpinningWheel)
            }

            val response = repository.getPkMoves(
                pokemonId = states.value.pokemon.id,
                generationName = "generation-ix",
                language = "en"
            )

            when (response) {
                is Resource.Error -> {
                    _pkMovesStates.update { currentState ->
                        currentState.copy(
                            errorState = response.networkErrorType.retryFullScreen(
                                onAction = {
                                    getPokemonMoves()
                                    _pkMovesStates.update { it.copy(errorState = null) }
                                }
                            )
                        )
                    }
                }
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

                    _pkMovesStates.update {
                        it.copy(moveCriteria = types)
                    }
                }
            }

            _pkMovesStates.update {
                it.copy(loading = LoadingIndicator.None)
            }
        }
    }

    private fun toggleFilterMoveCheck(filter: MoveFilterModel) {
        val updateFilter = _pkMovesStates.value.moveCriteria.map {
            if (it.type.id == filter.type.id) {
                it.copy(checked = !it.checked)
            } else {
                it
            }
        }

        _pkMovesStates.update {
            it.copy(moveCriteria = updateFilter)
        }

        if (updateFilter.any { it.checked }) {
            _moves
                .flatMap { it.value }
                .forEach { moveDetails ->
                    updateFilter
                        .firstOrNull { it.type.id == moveDetails.type?.id }
                        ?.let { filter ->
                            moveDetails.visible = filter.checked
                        }
                }
        } else {
            _moves
                .flatMap { it.value }
                .forEach { moveDetails ->
                    moveDetails.visible = true
                }
        }
    }

    private fun toggleLearnMethodExpand(event: PokemonDetailsEvents.ToggleLearnMethodExpand) {
        val updateList = moves[event.learnMethod]
            ?.map { moveDetails ->
                moveDetails.copy(visible = event.isExpanded)
            }
            .orEmpty()

        _moves.replace(event.learnMethod, updateList)
    }
}
