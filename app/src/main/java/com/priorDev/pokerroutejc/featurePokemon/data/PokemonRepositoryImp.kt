package com.priorDev.pokerroutejc.featurePokemon.data

import com.priorDev.pokerroutejc.data.network.MakeRetrofitNetworkCall
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.IPokemonNameClient
import com.priorDev.pokerroutejc.data.network.pokemon.EvolutionResponse
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonDao
import com.priorDev.pokerroutejc.featurePokemon.data.database.toDB
import com.priorDev.pokerroutejc.featurePokemon.domain.AbilityDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveData
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonData
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonNameData
import com.priorDev.pokerroutejc.featurePokemon.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImp @Inject constructor(
    private val service: PokemonService,
    private val dao: PokemonDao,
    private val makeRetrofitNetworkCall: MakeRetrofitNetworkCall,
    private val graphQLClient: IPokemonNameClient
) : PokemonRepository {
    override suspend fun searchPokemonNameByMatch(name: String): Flow<ResourceFlow<List<PokemonNameData>>> {
        return flow {
            emit(ResourceFlow.Loading())

            val likeName = "%$name%"
            val pokemons = dao.getPokemonNameByMatch(likeName)
            if (pokemons.isNotEmpty()) {
                emit(ResourceFlow.Success(pokemons.map { it.toDomain() }.sortedBy { it.name }))
                emit(ResourceFlow.Loading(false))
                return@flow
            }

            var existsNextPage = true
            var offset = 0
            val limit = 1281

            while (existsNextPage) {
                val response = makeRetrofitNetworkCall {
                    service.getAllPokemons(
                        urlLimitOffset = "pokemon?offset=$offset&limit=$limit"
                    )
                }

                if (response is ResourceFlow.Success) {
                    response.data?.let { nameContainer ->
                        existsNextPage = nameContainer.next != null
                        dao.insert(nameContainer.pokemons.map { it.toDB() })
                        offset += limit
                    }
                } else {
                    existsNextPage = false
                }
            }

            val pokemonDB = dao.getPokemonNameByMatch(likeName)

            emit(
                ResourceFlow.Success(
                    pokemonDB
                        .map { it.toDomain() }
                        .sortedBy { it.name }
                )
            )

            emit(ResourceFlow.Loading(false))
        }
    }

    override suspend fun getPokemon(pokemonName: String): Flow<ResourceFlow<PokemonData>> {
        return flow {
            emit(ResourceFlow.Loading())
            val response = makeRetrofitNetworkCall {
                service.getPokemon(pokemonName)
            }

            if (response is ResourceFlow.Success) {
                response.data?.let { pokemon ->
                    emit(ResourceFlow.Success(pokemon.toDomain()))
                }
            } else if (response is ResourceFlow.Error) {
                val error = response.uiMessages
                emit(ResourceFlow.Error(uiMessages = error, throwable = response.throwable))
            }

            emit(ResourceFlow.Loading(false))
        }
    }

    override suspend fun getPokemonNamePaging(offSet: Int): Flow<ResourceFlow<List<PokemonNameData>>> {
        return flow {
            emit(ResourceFlow.Loading())

            val response = makeRetrofitNetworkCall {
                service.getAllPokemons("pokemon?offset=$offSet&limit=$PAGINATION_POKEMON")
            }

            if (response is ResourceFlow.Success) {
                response.data?.let { container ->
                    emit(ResourceFlow.Success(container.pokemons.map { it.toDomain() }))
                }
            } else if (response is ResourceFlow.Error) {
                val error = response.uiMessages
                emit(ResourceFlow.Error(uiMessages = error, throwable = response.throwable))
            }

            emit(ResourceFlow.Loading(false))
        }
    }

    override suspend fun getMoveDetails(moves: List<MoveData>): Flow<ResourceFlow<MoveDetailsData>> {
        return flow {
            emit(ResourceFlow.Loading())

            moves.forEach { move ->
                val response = makeRetrofitNetworkCall {
                    service.getMoveDetails(move.id)
                }

                if (response is ResourceFlow.Success) {
                    response.data?.let {
                        emit(ResourceFlow.Success(it.toDomain(move)))
                    }
                }
            }

            emit(ResourceFlow.Loading(false))
        }
    }

    override suspend fun getAbility(ability: String): Flow<ResourceFlow<AbilityDetailsData>> = flow {
        emit(ResourceFlow.Loading())

        val response = makeRetrofitNetworkCall {
            service.getAbility(ability)
        }

        if (response is ResourceFlow.Success) {
            response.data?.let {
                emit(ResourceFlow.Success(it.toDomain()))
            }
        } else if (response is ResourceFlow.Error) {
            val error = response.uiMessages
            emit(ResourceFlow.Error(uiMessages = error, throwable = response.throwable))
        }

        emit(ResourceFlow.Loading(false))
    }

    override suspend fun getEvolutionChain(id: Int): ResourceFlow<Map<Int?, List<EvolutionResponse>>> {
        return graphQLClient.getEvolutionChain(id)
    }

    companion object {
        private const val PAGINATION_POKEMON = 20
    }
}
