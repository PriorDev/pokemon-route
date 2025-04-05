package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.utils.ApiLanguages
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    fun getAppLanguage(): Flow<ApiLanguages>
    suspend fun updateLanguage(language: ApiLanguages)
}
