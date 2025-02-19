package com.priorDev.pokerroutejc.data.network.pokemon

import com.apollographql.apollo3.api.Optional
import com.priorDev.GetEvolutionChainIdQuery
import com.priorDev.GetPkMovesQuery
import com.priorDev.SearchPokemonNameQuery
import com.priorDev.pokerroutejc.Resource
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.ApolloCaller
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.data.network.pokemon.responses.toResponse
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonNameData
import com.priorDev.pokerroutejc.domain.pokemon.models.toDomain
import com.priorDev.pokerroutejc.domain.pokemon.models.toModel

@Suppress("TooGenericExceptionCaught")
class PokemonApolloServiceImp(
    private val apolloCaller: ApolloCaller
) : PokemonApolloService {
    override suspend fun getPokemonByName(name: String): ResourceFlow<List<PokemonNameData>> {
        val response = apolloCaller.invoke(
            SearchPokemonNameQuery("%$name%")
        )

        return when (response) {
            is NetworkResource.Fail -> {
                return ResourceFlow.Error(
                    networkErrorType = response.error,
                    throwable = response.exception
                )
            }

            is NetworkResource.Success -> {
                ResourceFlow.Success(
                    response.data
                        .pokemon_v2_pokemon
                        .map { it.toDomain() }
                        .sortedBy { it.name }
                )
            }
        }
    }

    override suspend fun getEvolutionChain(pokemonId: Int): ResourceFlow<Map<Int?, List<EvolutionResponse>>> {
        val response = apolloCaller.invoke(
            GetEvolutionChainIdQuery(Optional.present(pokemonId))
        )

        return when (response) {
            is NetworkResource.Fail -> {
                return ResourceFlow.Error(
                    networkErrorType = response.error,
                    throwable = response.exception
                )
            }

            is NetworkResource.Success -> {
                val evolutionsList = response.data.pokemon_v2_pokemonspecies
                    .asSequence()
                    .map {
                        it.pokemon_v2_evolutionchain
                    }
                    .mapNotNull {
                        it?.pokemon_v2_pokemonspecies
                    }
                    .flatten()
                    .map {
                        it.toResponse()
                    }
                    .groupBy { it.evolvesFromSpecieId }

                ResourceFlow.Success(evolutionsList)
            }
        }
    }

    override suspend fun getPkMoves(
        pokemonId: Int,
        generationName: String,
        language: String
    ): Resource<Map<String, List<MoveDetailsData>>> {
        val response = apolloCaller.invoke(
            GetPkMovesQuery(
                Optional.present(pokemonId),
                Optional.present(generationName),
                Optional.present(language)
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
                val moveList = response.data
                    .pokemon_v2_pokemon
                    .flatMap { it.pokemon_v2_pokemonmoves }
                    .map {
                        it.toModel()
                    }
                    .groupBy { it.learnMethod }

                Resource.Success(moveList)
            }
        }
    }
}
