package com.priorDev.pokerroutejc.featurePokemon.data

import com.priorDev.pokerroutejc.data.network.MakeRetrofitNetworkCall
import com.priorDev.pokerroutejc.core.Resource
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
    private val makeRetrofitNetworkCall: MakeRetrofitNetworkCall
) : PokemonRepository {
    override suspend fun searchPokemonNameByMatch(name: String): Flow<Resource<List<PokemonNameData>>> {
        return flow {
            emit(Resource.Loading())

            val likeName = "%$name%"
            val pokemons = dao.getPokemonNameByMatch(likeName)
            if (pokemons.isNotEmpty()) {
                emit(Resource.Success(pokemons.map { it.toDomain() }.sortedBy { it.name }))
                emit(Resource.Loading(false))
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

                if (response is Resource.Success) {
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
                Resource.Success(
                    pokemonDB
                        .map { it.toDomain() }
                        .sortedBy { it.name }
                )
            )

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getPokemon(pokemonName: String): Flow<Resource<PokemonData>> {
        return flow {
            emit(Resource.Loading())
            val response = makeRetrofitNetworkCall {
                service.getPokemon(pokemonName)
            }

            if (response is Resource.Success) {
                response.data?.let { pokemon ->
                    emit(Resource.Success(pokemon.toDomain()))
                }
            } else if (response is Resource.Error) {
                val error = response.uiMessages
                emit(Resource.Error(uiMessages = error, throwable = response.throwable))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getPokemonNamePaging(offSet: Int): Flow<Resource<List<PokemonNameData>>> {
        return flow {
            emit(Resource.Loading())

            val response = makeRetrofitNetworkCall {
                service.getAllPokemons("pokemon?offset=$offSet&limit=$PAGINATION_POKEMON")
            }

            if (response is Resource.Success) {
                response.data?.let { container ->
                    emit(Resource.Success(container.pokemons.map { it.toDomain() }))
                }
            } else if (response is Resource.Error) {
                val error = response.uiMessages
                emit(Resource.Error(uiMessages = error, throwable = response.throwable))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMoveDetails(moves: List<MoveData>): Flow<Resource<MoveDetailsData>> {
        return flow {
            emit(Resource.Loading())

            moves.forEach { move ->
                val response = makeRetrofitNetworkCall {
                    service.getMoveDetails(move.id)
                }

                if (response is Resource.Success) {
                    response.data?.let {
                        emit(Resource.Success(it.toDomain(move)))
                    }
                }
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getAbility(ability: String): Flow<Resource<AbilityDetailsData>> = flow {
        emit(Resource.Loading())

        val response = makeRetrofitNetworkCall {
            service.getAbility(ability)
        }

        if (response is Resource.Success) {
            response.data?.let {
                emit(Resource.Success(it.toDomain()))
            }
        } else if (response is Resource.Error) {
            val error = response.uiMessages
            emit(Resource.Error(uiMessages = error, throwable = response.throwable))
        }

        emit(Resource.Loading(false))
    }

    companion object {
        private const val PAGINATION_POKEMON = 20
    }
}
