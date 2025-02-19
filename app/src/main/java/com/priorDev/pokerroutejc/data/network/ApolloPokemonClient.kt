package com.priorDev.pokerroutejc.data.network

import com.apollographql.apollo3.ApolloClient
import com.priorDev.SearchPokemonNameQuery
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.core.orDefault
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonNameData
import com.priorDev.pokerroutejc.featurePokemon.domain.toDomain
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class ApolloPokemonClient @Inject constructor(
    private val apolloClient: ApolloClient
) : IPokemonNameClient {
    override suspend fun getPokemonByName(name: String): ResourceFlow<List<PokemonNameData>> {
        return try {
            ResourceFlow.Success(
                apolloClient
                    .query(SearchPokemonNameQuery("%$name%"))
                    .execute()
                    .data
                    ?.pokemon_v2_pokemon
                    ?.map { it.toDomain() }
                    ?.sortedBy { it.name }
                    .orEmpty()
            )
        } catch (e: Exception) {
            ResourceFlow.Error(
                uiMessages = UiMessages.DynamicMessage(e.message.orDefault("Unknown error")),
                throwable = e
            )
        }
    }
}
