package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.data.dataStore.DataStoreKeys
import com.priorDev.pokerroutejc.data.dataStore.DataStoreManager
import com.priorDev.pokerroutejc.data.network.pokedex.PokedexApolloService
import com.priorDev.pokerroutejc.domain.pokedex.models.PokedexData
import com.priorDev.pokerroutejc.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.utils.ApiLanguages
import com.priorDev.pokerroutejc.utils.Resource
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