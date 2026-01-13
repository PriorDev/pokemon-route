package com.priorDev.pokerroutejc.core.data

import com.priorDev.pokerroutejc.core.domain.pokedex.models.PokedexData
import com.priorDev.pokerroutejc.core.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.core.utils.Resource

interface PokedexRepo {
    suspend fun getVersionGroups(): Resource<List<VersionGroupsData>>

    suspend fun getPokedexEntries(
        versionGroupId: Int
    ) : Resource<PokedexData>
}