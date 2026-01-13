package com.priorDev.pokerroutejc.core.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATA_STORE_NAME = "pk_data_store"
val Context.pkDataStore by preferencesDataStore(DATA_STORE_NAME)

class DataStoreManagerImp(
    private val dataStore: DataStore<Preferences>
) : DataStoreManager {
    override suspend fun <T> put(
        key: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun <T> remove(key: Preferences.Key<T>) {
        dataStore.edit {
            it.remove(key = key)
        }
    }

    override fun <T> get(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.map { it[key] }
    }
}
