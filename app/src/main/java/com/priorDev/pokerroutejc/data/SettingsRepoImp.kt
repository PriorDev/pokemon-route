package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.data.dataStore.DataStoreKeys
import com.priorDev.pokerroutejc.data.dataStore.DataStoreManager
import com.priorDev.pokerroutejc.utils.ApiLanguages
import com.priorDev.pokerroutejc.utils.toEnumLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepoImp(
    private val dataStore: DataStoreManager
) : SettingsRepo {
    override fun getAppLanguage(): Flow<ApiLanguages> {
        return dataStore.get(DataStoreKeys.LANGUAGE)
            .map {
                it.toEnumLanguage()
            }
    }

    override suspend fun updateLanguage(language: ApiLanguages) {
        dataStore.put(DataStoreKeys.LANGUAGE, language.key)
    }
}
