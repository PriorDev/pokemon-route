package com.priorDev.pokerroutejc.core.domain.pokedex.models

import com.priorDev.GetVersionGroupsQuery

data class VersionGroupsData(
    val id: Int = -1,
    val name: String = "",
    val generationName: String = ""
)

fun GetVersionGroupsQuery.Pokemon_v2_versiongroup.toVersionGroupsData(): VersionGroupsData {
    return VersionGroupsData(
        id = id,
        name = pokemon_v2_versions
            .first()
            .pokemon_v2_versionnames
            .first() // filter by language
            .name,
        generationName = pokemon_v2_generation?.name
            ?.substringAfterLast("-")
            ?.uppercase()
            .orEmpty()
    )
}
