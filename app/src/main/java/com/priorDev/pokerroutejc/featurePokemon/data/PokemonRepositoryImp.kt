package com.priorDev.pokerroutejc.featurePokemon.data

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.IPokemonNameClient
import com.priorDev.pokerroutejc.data.network.NetworkError
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.featurePokemon.domain.AbilityDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveData
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonData
import com.priorDev.pokerroutejc.featurePokemon.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImp @Inject constructor(
    private val service: IPokemonNetworkService,
    private val graphQLClient: IPokemonNameClient
) : PokemonRepository {
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
