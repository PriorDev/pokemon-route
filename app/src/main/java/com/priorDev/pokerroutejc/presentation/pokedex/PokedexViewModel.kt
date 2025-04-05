package com.priorDev.pokerroutejc.presentation.pokedex

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.data.PokedexRepo
import com.priorDev.pokerroutejc.domain.pokedex.models.PokedexEntriesData
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.retryFullScreen
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokedexViewModel(
    private val pokedexRepo: PokedexRepo,
    private val eventChannel: GlobalEventChannel,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _states = MutableStateFlow(PokedexStates())
    val states = _states.asStateFlow()

    private val _entries = mutableStateListOf<PokedexEntriesData>()
    val entries: List<PokedexEntriesData> = _entries

    init {
        val screenArgs = savedStateHandle.toRoute<Routes.Pokedex>()
        getPokedexEntries(screenArgs.versionGroupId)
    }

    fun onEvent(event: PokedexEvent) {
        when (event) {
            is PokedexEvent.OnNavigate -> eventChannel.navigate(event.route, event.navOptions)
        }
    }

    private fun getPokedexEntries(versionGroupId: Int) {
        viewModelScope.launch {
            _states.update { it.copy(loading = LoadingIndicator.SolidSpinningWheel) }

            when (val result = pokedexRepo.getPokedexEntries(versionGroupId)) {
                is Resource.Error -> {
                    _states.update { currentState ->
                        currentState.copy(
                            errorState = result.networkErrorType.retryFullScreen(
                                onAction = {
                                    _states.update { it.copy(loading = LoadingIndicator.None) }
                                    getPokedexEntries(versionGroupId)
                                }
                            )
                        )
                    }
                }

                is Resource.Success -> {
                    result.data?.let { response ->
                        _states.update {
                            it.copy(
                                pokedexStatus = response.status,
                                pokedexName = response.pokedexName
                            )
                        }

                        _entries.apply {
                            clear()
                            addAll(response.entries)
                        }
                    }
                }
            }

            _states.update { it.copy(loading = LoadingIndicator.None) }
        }
    }
}
