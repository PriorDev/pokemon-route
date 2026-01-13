package com.priorDev.pokerroutejc.core.data

import com.priorDev.pokerroutejc.core.data.dataStore.DataStoreKeys
import com.priorDev.pokerroutejc.core.data.dataStore.DataStoreManager
import com.priorDev.pokerroutejc.core.data.network.pokedex.PokedexApolloService
import com.priorDev.pokerroutejc.core.domain.pokedex.models.PokedexData
import com.priorDev.pokerroutejc.core.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.core.utils.ApiLanguages
import com.priorDev.pokerroutejc.core.utils.Resource
import kotlinx.coroutines.flow.first

class PokedexRepoImp(
    private val pokedexApolloService: PokedexApolloService,
    private val dataStore: DataStoreManager
): PokedexRepo {
    override suspend fun getVersionGroups(): Resource<List<VersionGroupsData>> {
        val language = dataStore.get(DataStoreKeys.LANGUAGE).first() ?: ApiLanguages.ENGLISH.key
        val response = pokedexApolloService.getVersionGroups(language)
        return response
    }

    override suspend fun getPokedexEntries(
        versionGroupId: Int
    ) : Resource<PokedexData> {
        val language = dataStore.get(DataStoreKeys.LANGUAGE).first() ?: ApiLanguages.ENGLISH.key

        return pokedexApolloService.getPokedexEntries(
            versionGroupId = versionGroupId,
            language = language
        )
    }
}