package com.prior_dev.pokerroutejc.repositories.pokemon_repo

import com.prior_dev.pokerroutejc.core.MakeNetworkCall
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonRepositoryImp
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonService
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonDao
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonNameEntity
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import com.prior_dev.pokerroutejc.feature_pokemon.domain.MoveData
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class PokeRepoGetMoveDetails {
    @Test
    fun getMoveDetailsSucess(): Unit = runBlocking {
        class FakeService: PokemonService {
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                return MoveDetailsResponse(
                    id = 1,
                    name = "",
                    accuracy = null,
                    damage_class = null,
                    effect_entries = null,
                    flavor_text_entries = null,
                    generation = null,
                    moveNamesResponses = null,
                    pastValues = null,
                    power = null,
                    pp = null,
                    priority = null,
                    type = null
                )
            }

        }
        val service = FakeService()

        class FakeDao: PokemonDao {
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                TODO("Not yet implemented")
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }

        }
        val dao = FakeDao()

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall())

        val moveList = listOf(
            MoveData("Teraexplosion", 1L, versionGroupDetails = emptyList()),
            MoveData("Teraexplosion", 2L, versionGroupDetails = emptyList()),
            MoveData("Teraexplosion", 3L, versionGroupDetails = emptyList()),
        )
        val resourceFlow = repo.getMoveDetails(moveList).toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        Assert.assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        Assert.assertEquals(false, isNotLoadingResource.isLoading)

        val responseSuccess = resourceFlow.filter { it is Resource.Success }
        Assert.assertEquals(moveList.count(), responseSuccess.count())

    }

    @Test
    fun getMoveDetailsFail(): Unit = runBlocking {
        class FakeService: PokemonService {
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                throw Exception("Error")
            }
        }
        val service = FakeService()

        class FakeDao: PokemonDao {
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                TODO("Not yet implemented")
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }

        }
        val dao = FakeDao()

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall())

        val moveList = listOf(
            MoveData("Teraexplosion", 1L, versionGroupDetails = emptyList()),
            MoveData("Teraexplosion", 2L, versionGroupDetails = emptyList()),
            MoveData("Teraexplosion", 3L, versionGroupDetails = emptyList()),
        )
        val resourceFlow = repo.getMoveDetails(moveList).toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        Assert.assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        Assert.assertEquals(false, isNotLoadingResource.isLoading)
    }
}