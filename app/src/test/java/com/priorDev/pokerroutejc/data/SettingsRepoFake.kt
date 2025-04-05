package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.utils.ApiLanguages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingsRepoFake : SettingsRepo {
    var getLanguageResponse: Flow<ApiLanguages> = flow { emit(ApiLanguages.ENGLISH) }
    override fun getAppLanguage(): Flow<ApiLanguages> {
        return getLanguageResponse
    }

    override suspend fun updateLanguage(language: ApiLanguages) {
        getLanguageResponse = flow { emit(language) }
    }
}
