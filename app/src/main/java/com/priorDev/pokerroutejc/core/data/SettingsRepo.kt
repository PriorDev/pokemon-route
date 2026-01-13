package com.priorDev.pokerroutejc.core.data

import com.priorDev.pokerroutejc.core.utils.ApiLanguages
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    fun getAppLanguage(): Flow<ApiLanguages>
    suspend fun updateLanguage(language: ApiLanguages)
}
