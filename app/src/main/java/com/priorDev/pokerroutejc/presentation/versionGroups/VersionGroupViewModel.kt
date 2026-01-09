package com.priorDev.pokerroutejc.presentation.versionGroups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priorDev.pokerroutejc.data.PokedexRepo
import com.priorDev.pokerroutejc.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.domain.utils.romanToDecimal
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.SortOrder
import com.priorDev.pokerroutejc.presentation.core.retryFullScreen
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VersionGroupViewModel(
    private val pokedexRepo: PokedexRepo,
    private val eventChannel: GlobalEventChannel
) : ViewModel() {
    private val _states = MutableStateFlow(VersionGroupStates())
    val states = _states.asStateFlow()

    init {
        getVersionGroups()
    }

    fun onEvent(versionGroupEvent: VersionGroupEvent) {
        when (versionGroupEvent) {
            is VersionGroupEvent.OnNavigate -> {
                eventChannel.navigate(versionGroupEvent.route, versionGroupEvent.navOptions)
            }

            VersionGroupEvent.OnToggleOrder -> {
                toggleSortOrder()
            }
        }
    }

    private fun toggleSortOrder() {
        _states.update { currentState ->
            val newOrder = if (currentState.sortOrder == SortOrder.Ascending) {
                SortOrder.Descending
            } else {
                SortOrder.Ascending
            }

            val currentList = currentState.versionGroupList.values.flatten()

            currentState.copy(
                sortOrder = newOrder,
                versionGroupList = sortVersionGroup(currentList, newOrder)
            )
        }
    }

    private fun getVersionGroups() {
        viewModelScope.launch {
            _states.update { it.copy(loading = LoadingIndicator.SolidSpinningWheel) }
            when (val resource = pokedexRepo.getVersionGroups()) {
                is Resource.Error -> {
                    _states.update {
                        it.copy(
                            errorState = resource.networkErrorType.retryFullScreen(
                                onAction = {
                                    viewModelScope.launch {
                                        getVersionGroups()
                                    }
                                    _states.update { it.copy(errorState = null) }
                                }
                            )
                        )
                    }
                }

                is Resource.Success -> {
                    val versions = resource.data.orEmpty()

                    _states.update {
                        it.copy(
                            versionGroupList = sortVersionGroup(versions, it.sortOrder)
                        )
                    }
                }
            }

            _states.update { it.copy(loading = LoadingIndicator.None) }
        }
    }

    private fun sortVersionGroup(
        data: List<VersionGroupsData>,
        sortOrder: SortOrder
    ): Map<String, List<VersionGroupsData>> {
        val sortedList = data.sortedWith(
            compareBy<VersionGroupsData> {
                val generationValue = it.generationName.romanToDecimal()
                if (sortOrder == SortOrder.Ascending) generationValue else -generationValue
            }.thenByDescending { it.id }
        )

        return sortedList.groupBy { it.generationName }
    }
}
