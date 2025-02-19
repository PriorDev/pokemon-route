package com.priorDev.pokerroutejc.domain.pokemon

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonNetService
import com.priorDev.pokerroutejc.data.database.PokemonDao
import com.priorDev.pokerroutejc.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.data.database.toDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration

@OptIn(ExperimentalPagingApi::class)
class PokemonNameRemoteMediator(
    private val pokemonNetService: PokemonNetService,
    private val pokemonDao: PokemonDao
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

        val response = pokemonNetService.getAllPokemons(
            offset = offset,
            limit = state.config.pageSize,
        )

        return when (response) {
            is NetworkResource.Fail -> {
                MediatorResult.Error(response.exception ?: Throwable("Unknown error"))
            }
            is NetworkResource.Success -> {
                val pokemons = response.data.pokemons.map { it.toDB() }

                pokemonDao.insert(pokemons)

                MediatorResult.Success(
                    endOfPaginationReached = response.data.next.isNullOrEmpty()
                )
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        val THRESHOLD = Duration.ofDays(10)
    }
}
