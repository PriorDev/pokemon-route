package com.priorDev.pokerroutejc.featurePokemon.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.priorDev.pokerroutejc.data.network.MakeRetrofitNetworkCall
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.featurePokemon.data.PokemonService
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonDao
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.featurePokemon.data.database.toDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration

@OptIn(ExperimentalPagingApi::class)
class PokemonNameRemoteMediator(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao,
    private val makeRetrofitNetworkCall: MakeRetrofitNetworkCall,
) : RemoteMediator<Int, PokemonNameEntity>() {

    private var isFirstTime = true

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonNameEntity>
    ): MediatorResult {
        return when (loadType) {
            LoadType.REFRESH -> {
                if (isFirstTime) {
                    isFirstTime = false
                    MediatorResult.Success(endOfPaginationReached = false)
                } else {
                    pokemonDao.eraseNames()
                    fetchData(state)
                }
            }

            LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> fetchData(state)
        }
    }

    private suspend fun fetchData(
        state: PagingState<Int, PokemonNameEntity>
    ): MediatorResult {
        val pokemonExpiredData = withContext(Dispatchers.Default) {
            pokemonDao.getExpiredPokemons(
                threshold = THRESHOLD.toMillis(),
            )
        }

        val minIdExpired = pokemonExpiredData.minByOrNull { it.id - 1 }?.id

        val offset = minIdExpired
            ?: state.lastItemOrNull()?.id
            ?: 0

        val response = makeRetrofitNetworkCall {
            pokemonService.getAllPokemons(
                urlLimitOffset = "pokemon?offset=$offset&limit=${state.config.pageSize}"
            )
        }

        return when (response) {
            is ResourceFlow.Loading -> MediatorResult.Error(Throwable("Unknown error"))

            is ResourceFlow.Error -> {
                MediatorResult.Error(response.throwable ?: Throwable("Unknown error"))
            }

            is ResourceFlow.Success -> {
                val pokemons = response.data?.pokemons?.map { it.toDB() }

                if (pokemons != null) {
                    pokemonDao.insert(pokemons)
                }

                MediatorResult.Success(
                    endOfPaginationReached = response.data?.next.isNullOrEmpty()
                )
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        val THRESHOLD = Duration.ofDays(10)
    }
}
