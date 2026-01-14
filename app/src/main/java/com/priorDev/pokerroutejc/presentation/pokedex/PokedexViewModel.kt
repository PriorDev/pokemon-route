package com.priorDev.pokerroutejc.presentation.pokedex

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.data.PokedexRepo
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

    private val versionGroupId: Int

    init {
        val screenArgs = savedStateHandle.toRoute<Routes.Pokedex>()
        versionGroupId = screenArgs.versionGroupId
        getPokedexEntries(versionGroupId)
    }

    fun onEvent(event: PokedexEvent) {
        when (event) {
            is PokedexEvent.OnNavigate -> eventChannel.navigate(event.route, event.navOptions)
            PokedexEvent.OnRetryGetPokedexEntries -> {
                 _states.update { it.copy(loading = LoadingIndicator.None, errorState = null) }
                 getPokedexEntries(versionGroupId)
            }
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
                                actionEvent = PokedexEvent.OnRetryGetPokedexEntries
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
