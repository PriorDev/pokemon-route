package com.priorDev.pokerroutejc.features.pokedex.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.core.data.PokedexRepo
import com.priorDev.pokerroutejc.core.presentation.components.LoadingIndicator
import com.priorDev.pokerroutejc.core.presentation.components.retryFullScreen
import com.priorDev.pokerroutejc.navigation.Routes
import com.priorDev.pokerroutejc.core.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.core.utils.Resource
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
                                pokedexName = response.pokedexName,
                                entries = response.entries
                            )
                        }
                    }
                }
            }

            _states.update { it.copy(loading = LoadingIndicator.None) }
        }
    }
}
