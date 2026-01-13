package com.priorDev.pokerroutejc.core.data

import com.priorDev.pokerroutejc.core.data.dataStore.DataStoreKeys
import com.priorDev.pokerroutejc.core.data.dataStore.DataStoreManager
import com.priorDev.pokerroutejc.core.utils.ApiLanguages
import com.priorDev.pokerroutejc.core.utils.toEnumLanguage
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
