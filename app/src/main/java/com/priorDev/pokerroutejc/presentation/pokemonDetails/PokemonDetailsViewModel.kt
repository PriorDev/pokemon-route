package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.PokedexRepo
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonApolloService
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonData
import com.priorDev.pokerroutejc.domain.pokemon.models.createMoveList
import com.priorDev.pokerroutejc.domain.types.models.DamageValue
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution.EvolutionState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.MoveFilterModel
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PokemonMovesState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites.SpritesState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation.DamageRelationStates
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.ApiLanguages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val pokemonApolloService: PokemonApolloService,
    private val pokedexRepo: PokedexRepo,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _states = MutableStateFlow(PokemonDetailsStates())
    val states = _states.asStateFlow()

    private val _pkMovesStates = MutableStateFlow(PokemonMovesState())
    val pkMovesStates = _pkMovesStates.asStateFlow()

    private val _evolutionState = MutableStateFlow(EvolutionState())
    val evolutionState = _evolutionState.asStateFlow()

    private val _spritesState = MutableStateFlow(SpritesState())
    val spritesState = _spritesState.asStateFlow()

    private val _damageRelationStates = MutableStateFlow(DamageRelationStates())
    val damageRelationStates = _damageRelationStates.asStateFlow()

    private val _moves = mutableMapOf<String, List<MoveDetailsData>>()
    val moves: Map<String, List<MoveDetailsData>> = _moves

    private val _selectedLanguage = MutableStateFlow(ApiLanguages.ENGLISH)
    val selectedLanguage = _selectedLanguage.asStateFlow()

    init {
        val args = savedStateHandle.toRoute<Routes.PkDetails>()
        getPokemonDetails(args.pokemonName)
    }

    fun onEvent(event: PokemonDetailsEvents) {
        when (event) {
            is PokemonDetailsEvents.OnFilterMoves -> {
                filterMoves(event.criteria)
            }

            is PokemonDetailsEvents.OnMoveClick -> {
                _pkMovesStates.update { it.copy(selectedMove = event.move) }
            }

            PokemonDetailsEvents.OnDismiss -> {
                _states.update { it.copy(uiMessages = null) }
                _pkMovesStates.update { it.copy(selectedMove = null) }
            }

            is PokemonDetailsEvents.OnSelectLanguage -> {
                _selectedLanguage.value = event.language
                _pkMovesStates.update { it.copy(selectedMove = null) }
            }
        }
    }

    private fun getPokemonDetails(name: String) {
        viewModelScope.launch {
            _states.update { it.copy(loading = LoadingIndicator.SolidSpinningWheel) }

            when (val result = pokemonApolloService.getPokemonDetails(name)) {
                is ResourceFlow.Error -> {
                    _states.update { it.copy(uiMessages = result.uiMessages) }
                }

                is ResourceFlow.Loading -> {
                    _states.update { it.copy(loading = result.loadingIndicator) }
                }

                is ResourceFlow.Success -> {
                    result.data?.let { data ->
                        _states.update {
                            it.copy(
                                pokemon = data.pokemon
                            )
                        }

                        // Update Sub-States
                        _evolutionState.update {
                            it.copy(evolutions = data.evolutions)
                        }

                        _spritesState.update {
                            it.copy(sprites = data.pokemon.sprites, name = data.pokemon.name)
                        }

                        _moves.clear()
                        _moves.putAll(data.moves)
                        updateDamageRelation(data.damageRelations)
                        _pkMovesStates.update {
                            it.copy(
                                moveCriteria = getMoveFilters(data.moves.keys)
                            )
                        }
                    }
                }
            }
            _states.update { it.copy(loading = LoadingIndicator.None) }
        }
    }

    private fun updateDamageRelation(damageRelations: Map<UiMessages, List<DamageValue>>) {
        _damageRelationStates.update {
            it.copy(
                damageRelations = damageRelations
            )
        }
    }

    private fun getMoveFilters(moveKeys: Set<String>): List<MoveFilterModel> {
        return moveKeys.mapIndexed { index, move ->
            MoveFilterModel(
                id = index,
                name = move,
                isSelected = index == 0
            )
        }
    }

    private fun filterMoves(criteria: MoveFilterModel) {
        val currentCriteria = _pkMovesStates.value.moveCriteria.map {
            it.copy(isSelected = it.id == criteria.id)
        }

        val pokemon = _states.value.pokemon
        val filteredMoves = pokemon.moves.filter {
            it.moveLearnMethod == criteria.name
        }.createMoveList()

        // Although we are not updating a list in state directly (moves are in map),
        // the view uses the criteria to pick from the map.
        // Wait, `PokemonMovesView` takes `movesList` (Map).
        // And uses `pkMovesState.moveCriteria` to filter.
        // So we just update criteria selection.

        _pkMovesStates.update {
            it.copy(moveCriteria = currentCriteria)
        }
    }
}
