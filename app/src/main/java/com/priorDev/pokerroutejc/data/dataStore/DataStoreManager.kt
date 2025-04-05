package com.priorDev.pokerroutejc.data.dataStore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun <T> put(
        key: Preferences.Key<T>,
        value: T
    )

    suspend fun <T> remove(key: Preferences.Key<T>)

    fun <T> get(key: Preferences.Key<T>): Flow<T?>
}
