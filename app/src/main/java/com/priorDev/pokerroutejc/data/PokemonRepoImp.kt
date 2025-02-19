package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonApolloService
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonNetService
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveData
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonData
import com.priorDev.pokerroutejc.domain.pokemon.models.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepoImp @Inject constructor(
    private val service: PokemonNetService,
    private val graphQLClient: PokemonApolloService
) : PokemonRepo {
    override suspend fun getPokemon(pokemonName: String): Flow<ResourceFlow<PokemonData>> {
        return flow {
            emit(ResourceFlow.Loading())
            when (val response = service.getPokemon(pokemonName)) {
                is NetworkResource.Fail -> {
                    emit(ResourceFlow.Error(networkErrorType = NetworkError.UnknownError))
                }

                is NetworkResource.Success -> {
                    emit(ResourceFlow.Success(response.data.toDomain()))
                }
            }

            emit(ResourceFlow.Loading(false))
        }
    }

    override suspend fun getMoveDetails(moves: List<MoveData>): Flow<ResourceFlow<MoveDetailsData>> {
        return flow {
            emit(ResourceFlow.Loading())

            moves.forEach { move ->
                val response = service.getMoveDetails(move.id.toString())

                if (response is NetworkResource.Success) {
                    emit(ResourceFlow.Success(response.data.toDomain(move)))
                }
            }

            emit(ResourceFlow.Loading(false))
        }
    }

    override suspend fun getAbility(ability: String): Flow<ResourceFlow<AbilityDetailsData>> = flow {
        emit(ResourceFlow.Loading())

        val response = service.getAbility(ability)

        when (response) {
            is NetworkResource.Fail -> {
                emit(ResourceFlow.Error(networkErrorType = NetworkError.UnknownError))
            }
            is NetworkResource.Success -> {
                emit(ResourceFlow.Success(response.data.toDomain()))
            }
        }

        emit(ResourceFlow.Loading(false))
    }

    override suspend fun getEvolutionChain(id: Int): ResourceFlow<Map<Int?, List<EvolutionResponse>>> {
        return graphQLClient.getEvolutionChain(id)
    }
}
