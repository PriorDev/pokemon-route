package com.priorDev.pokerroutejc.core.data.network.pokedex

import com.priorDev.pokerroutejc.core.domain.pokedex.models.PokedexData
import com.priorDev.pokerroutejc.core.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.core.utils.Resource

interface PokedexApolloService {
    suspend fun getVersionGroups(
        language: String
    ): Resource<List<VersionGroupsData>>

    suspend fun getPokedexEntries(
        versionGroupId: Int,
        language: String
    ) : Resource<PokedexData>
}
