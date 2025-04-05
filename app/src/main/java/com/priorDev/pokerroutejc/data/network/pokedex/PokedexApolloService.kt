package com.priorDev.pokerroutejc.data.network.pokedex

import com.priorDev.pokerroutejc.domain.pokedex.models.PokedexData
import com.priorDev.pokerroutejc.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.utils.Resource

interface PokedexApolloService {
    suspend fun getVersionGroups(
        language: String
    ): Resource<List<VersionGroupsData>>

    suspend fun getPokedexEntries(
        versionGroupId: Int,
        language: String
    ) : Resource<PokedexData>
}
