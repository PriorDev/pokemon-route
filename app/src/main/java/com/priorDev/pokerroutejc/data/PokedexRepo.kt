package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.domain.pokedex.models.PokedexData
import com.priorDev.pokerroutejc.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.utils.Resource

interface PokedexRepo {
    suspend fun getVersionGroups(): Resource<List<VersionGroupsData>>

    suspend fun getPokedexEntries(
        versionGroupId: Int
    ) : Resource<PokedexData>
}