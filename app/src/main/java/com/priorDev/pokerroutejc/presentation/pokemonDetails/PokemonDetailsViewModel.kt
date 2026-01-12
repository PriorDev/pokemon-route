package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.LoadState
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.core.getDamageTitle
import com.priorDev.pokerroutejc.data.PokedexRepo
import com.priorDev.pokerroutejc.data.PokemonRepo
import com.priorDev.pokerroutejc.data.SettingsRepo
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonApolloService
import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.useCases.PokemonUseCases
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.retryFullScreen
import com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution.EvolutionState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.MoveFilterModel
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PokemonMovesState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites.SpritesState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation.DamageRelationStates
import com.priorDev.pokerroutejc.presentation.utils.flowSubscriber
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.ApiLanguages
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepo,
    private val useCases: PokemonUseCases,
    private val globalEvent: GlobalEventChannel,
    private val settingsRepo: SettingsRepo,
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

    private val _moves = mutableStateMapOf<String, List<MoveDetailsData>>()
    val moves: Map<String, List<MoveDetailsData>> = _moves

    val selectedLanguage = settingsRepo
        .getAppLanguage()
        .flowSubscriber(ApiLanguages.ENGLISH)

    init {
        viewModelScope.launch {
            val navArg = savedStateHandle.toRoute<Routes.PkDetails>()
            repository.getPokemon(navArg.pokemonName).collect { resource ->
                _states.update { it.copy(loading = LoadingIndicator.SolidSpinningWheel) }
                when (resource) {
                    is ResourceFlow.Error -> { }
                    is ResourceFlow.Loading -> { }
                    is ResourceFlow.Success -> {
                        resource.data?.let { data ->
                            _states.update { it.copy(pokemon = data) }
                            // Sub-states updates
                            _spritesState.update {
                                it.copy(sprites = data.sprites, name = data.name)
                            }
                        }
                    }
                }
                _states.update { it.copy(loading = LoadingIndicator.None) }
            }

            launch {
                getDamageRelation()
            }

            launch {
                observeLanguageChanges()
            }

            launch {
                getEvolutions()
            }
        }
    }

    private suspend fun observeLanguageChanges() {
        selectedLanguage.collectLatest {
            getPokemonMoves()
        }
    }

    private suspend fun getEvolutions() {
        val pokemonId = states.value.pokemon.id ?: return

        when (val response = repository.getEvolutionChain(pokemonId)) {
            is ResourceFlow.Error -> { }

            is ResourceFlow.Loading -> { }

            is ResourceFlow.Success -> {
                _evolutionState.update {
                    it.copy(evolutions = response.data.orEmpty())
                }
            }
        }
    }

    fun onEvent(event: PokemonDetailsEvents) {
        when (event) {
            is PokemonDetailsEvents.Navigate -> {
                globalEvent.navigate(event.route, event.navOptions)
            }

            is PokemonDetailsEvents.ToggleLearnMethodExpand -> toggleLearnMethodExpand(event)

            is PokemonDetailsEvents.ToggleMoveFilterCheck -> toggleFilterMoveCheck(event.filter)

            is PokemonDetailsEvents.SelectLanguage -> setSelectedLanguages(event)

            is PokemonDetailsEvents.SelectMove -> selectMove(event)

            // untested
            is PokemonDetailsEvents.OnGenerationSelect -> { }

            is PokemonDetailsEvents.OnAbilityClick -> onAbilityClick(event.ability)

            PokemonDetailsEvents.OnAbilityDismiss -> onAbilityDismiss()

            is PokemonDetailsEvents.OnSearchTextChange -> onSearchTextChanged(event.text)
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

    // -------------| Refactored

    private fun getDamageRelation() {
        viewModelScope.launch {
            _damageRelationStates.update {
                it.copy(loading = LoadingIndicator.SolidSpinningWheel)
            }

            when (val resource = useCases.getDamageRelations(states.value.pokemon.types)) {
                is Resource.Error -> {
                    _damageRelationStates.update {
                        it.copy(
                            errorState = resource.networkErrorType.retryFullScreen(
                                onAction = {
                                    getDamageRelation()
                                    _damageRelationStates.update { it.copy(errorState = null) }
                                }
                            )
                        )
                    }
                }

                is Resource.Success -> {
                    val damageRelation = resource.data
                        ?.groupBy { it.damageValue }
                        ?.entries
                        ?.sortedByDescending { it.key }
                        ?.associate { it.key.getDamageTitle() to it.value }
                        .orEmpty()

                    _damageRelationStates.update {
                        it.copy(damageRelations = damageRelation)
                    }
                }
            }

            _damageRelationStates.update {
                it.copy(loading = LoadingIndicator.None)
            }
        }
    }

    private fun setSelectedLanguages(event: PokemonDetailsEvents.SelectLanguage) {
        viewModelScope.launch {
            settingsRepo.updateLanguage(event.language)
        }
    }

    private fun getPokemonMoves() {
        viewModelScope.launch {
            val pokemonId = states.value.pokemon.id ?: return@launch

            _pkMovesStates.update {
                it.copy(loading = LoadingIndicator.SolidSpinningWheel)
            }

            val response = repository.getPkMoves(
                pokemonId = pokemonId,
                generationName = "generation-ix",
                language = selectedLanguage.value.key
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
                    _moves.apply {
                        clear()
                        putAll(response.data.orEmpty())
                    }
                }
            }

            val types = moves
                .asSequence()
                .flatMap { it.value }
                .mapNotNull { it.type }
                .distinct()
                .sortedBy { it.name }
                .map {
                    MoveFilterModel(
                        checked = false,
                        type = it
                    )
                }
                .toList()
                .toList()

            _pkMovesStates.update {
                it.copy(
                    moveCriteria = types,
                    loading = LoadingIndicator.None
                )
            }
        }
    }

    private fun toggleFilterMoveCheck(filter: MoveFilterModel) {
        val updateFilters = _pkMovesStates.value.moveCriteria.map { moveCriteria ->
            if (moveCriteria.type.id == filter.type.id) {
                moveCriteria.copy(checked = moveCriteria.checked.not())
            } else {
                moveCriteria
            }
        }

        _pkMovesStates.update {
            it.copy(moveCriteria = updateFilters)
        }

        if (updateFilters.any { it.checked }) {
            _moves
                .flatMap { it.value }
                .forEach { moveDetails ->
                    updateFilters
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

    private fun selectMove(event: PokemonDetailsEvents.SelectMove) {
        _pkMovesStates.update { it.copy(selectedMove = event.move) }
    }
}
