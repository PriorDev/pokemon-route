package com.priorDev.pokerroutejc.core.data.network.pokedex

import com.apollographql.apollo3.api.Optional
import com.priorDev.GetPokedexEntriesQuery
import com.priorDev.GetVersionGroupsQuery
import com.priorDev.pokerroutejc.core.data.network.ApolloCaller
import com.priorDev.pokerroutejc.core.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.core.domain.pokedex.models.PokedexData
import com.priorDev.pokerroutejc.core.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.core.domain.pokedex.models.toPokedexData
import com.priorDev.pokerroutejc.core.domain.pokedex.models.toVersionGroupsData
import com.priorDev.pokerroutejc.core.utils.Resource

class PokedexApolloServicesImp(
    private val apolloCaller: ApolloCaller
): PokedexApolloService {
    override suspend fun getVersionGroups(
        language: String
    ): Resource<List<VersionGroupsData>> {
        val response = apolloCaller.invoke(
            GetVersionGroupsQuery(Optional.present(language))
        )

        return when (response) {
            is NetworkResource.Fail -> Resource.Error(
                networkErrorType = response.error,
                throwable = response.exception
            )

            is NetworkResource.Success -> {
                val versionsGroup = response.data
                    .pokemon_v2_versiongroup
                    .map {
                        it.toVersionGroupsData()
                    }

                Resource.Success(versionsGroup)
            }
        }
    }

    override suspend fun getPokedexEntries(
        versionGroupId: Int,
        language: String
    ) : Resource<PokedexData> {
        val response = apolloCaller.invoke(
            GetPokedexEntriesQuery(
                Optional.present(versionGroupId),
                Optional.present(language),
            )
        )

        return when (response) {
            is NetworkResource.Fail -> {
                Resource.Error(
                    networkErrorType = response.error,
                    throwable = response.exception
                )
            }

            is NetworkResource.Success -> {
                val pokedex = response.data.pokemon_v2_pokedex
                    .firstOrNull() // Filter by version group id
                    ?.toPokedexData()

                Resource.Success(pokedex)
            }
        }
    }
}
