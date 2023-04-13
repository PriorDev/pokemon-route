package com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.search

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.prior_dev.pokemonrroutejc.core.CommonStates
import com.prior_dev.pokemonrroutejc.core.handleResource
import com.prior_dev.pokemonrroutejc.feature_pokemon.data.PokemonRepositoryImp
import com.prior_dev.pokemonrroutejc.feature_pokemon.domain.PokemonNameData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    private val repository: PokemonRepositoryImp
): ViewModel() {
    private val _states = MutableLiveData(CommonStates())
    val states: LiveData<CommonStates> = _states

    private val _pokemonNames = mutableStateListOf<PokemonNameData>()
    val pokemonNames: List<PokemonNameData> = _pokemonNames

    private var searchJob: Job? = null

    init {
        _states.value = states.value?.copy(isLoading = false)
        viewModelScope.launch {
            repository.getPokemonNamePaging(0).collect{ result ->
                handleResource(result, _states = _states, states = states){
                    result.data?.let {
                        _pokemonNames.clear()
                        _pokemonNames.addAll(it)
                    }
                }
            }
        }
    }

    fun onSearchText(text: String){
        _states.value = states.value?.copy(searchText = text)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            if(text.isBlank()){
                repository.getPokemonNamePaging(0).collect{ result ->
                    handleResource(result, _states, states){
                        result.data?.let {
                            _pokemonNames.clear()
                            _pokemonNames.addAll(it)
                        }
                    }
                }
            }else{
                repository.searchPokemonNameByMatch(text)
                    .collect{ result ->
                        handleResource(result, _states, states){
                            result.data?.let {
                                _pokemonNames.clear()
                                _pokemonNames.addAll(it)
                            }
                        }
                    }
            }

        }
    }

    fun onDismiss(){
        _states.value = states.value?.copy(message = "")
    }
}