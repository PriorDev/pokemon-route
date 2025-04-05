package com.priorDev.pokerroutejc.presentation.versionGroups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priorDev.pokerroutejc.data.PokedexRepo
import com.priorDev.pokerroutejc.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.retryFullScreen
import com.priorDev.pokerroutejc.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VersionGroupViewModel(
    private val pokedexRepo: PokedexRepo
): ViewModel() {
    private val _states = MutableStateFlow(VersionGroupStates())
    val states = _states.asStateFlow()

    private val _versionGroupList = mutableMapOf<String, List<VersionGroupsData>>()
    val versionGroupList: Map<String, List<VersionGroupsData>> = _versionGroupList

    init {
        viewModelScope.launch {
            getVersionGroups()
        }
    }

    private suspend fun getVersionGroups() {
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
                    val versions = resource.data
                        ?.groupBy { it.generationName }
                        .orEmpty()

                    _versionGroupList.apply {
                        clear()
                        putAll(versions)
                    }
                }
            }

            _states.update { it.copy(loading = LoadingIndicator.None) }
        }
    }
}