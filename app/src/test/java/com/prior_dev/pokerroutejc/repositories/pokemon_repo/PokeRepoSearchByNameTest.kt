package com.prior_dev.pokerroutejc.repositories.pokemon_repo

import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonRepositoryImp
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonService
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonDao
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonNameEntity
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigInteger

class PokeRepoSearchByNameTest {
    @Test
    fun searchPokemonNameByMatchSuccessWithNoLocalData(): Unit = runBlocking {
        class FakeService: PokemonService{
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                return ContainerPokemonNameResponse(
                    next = null,
                    pokemons = listOf(
                        PokemonNameResponse("https://pokeapi.co/api/v2/pokemon/1/", "bulbasaur"),
                        PokemonNameResponse("https://pokeapi.co/api/v2/pokemon/2/", "ivysaur"),
                        PokemonNameResponse("https://pokeapi.co/api/v2/pokemon/3/", "venusaur"),
                    )
                )
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            var insert = false
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                insert = true
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                return emptyList()
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }
        }
        val dao = FakeDao()

        val repo = PokemonRepositoryImp(service, dao)

        val resourceFlow = repo.searchPokemonNameByMatch("test").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        assertEquals(false, isNotLoadingResource.isLoading)

        assertEquals(true, dao.insert)

        val responseSuccess = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, responseSuccess.count())
    }

    @Test
    fun searchPokemonNameByMatchSuccessWithLocalData(): Unit = runBlocking {
        class FakeService: PokemonService{
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                return ContainerPokemonNameResponse(
                    next = null,
                    pokemons = listOf(
                        PokemonNameResponse("https://pokeapi.co/api/v2/pokemon/1/", "bulbasaur"),
                        PokemonNameResponse("https://pokeapi.co/api/v2/pokemon/2/", "ivysaur"),
                        PokemonNameResponse("https://pokeapi.co/api/v2/pokemon/3/", "venusaur"),
                    )
                )
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            var insert = false
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                insert = true
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                return listOf(
                    PokemonNameEntity(1, "fire"),
                    PokemonNameEntity(2, "ice"),
                    PokemonNameEntity(3, "poison"),
                )
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }
        }
        val dao = FakeDao()

        val repo = PokemonRepositoryImp(service, dao)

        val resourceFlow = repo.searchPokemonNameByMatch("test").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        assertEquals(false, isNotLoadingResource.isLoading)

        //TODO:
//        assertEquals(true, dao.insert)

        val responseSuccess = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, responseSuccess.count())

        val pokemon = responseSuccess.first() as Resource.Success
        assertEquals(3, pokemon.data!!.count())
    }

    @Test
    fun searchPokemonNameByMatchFailWithLocalData(): Unit = runBlocking {
        class FakeService: PokemonService{
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                throw Exception("Error")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            var insert = false
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                insert = true
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                return listOf(
                    PokemonNameEntity(1, "fire"),
                    PokemonNameEntity(2, "ice"),
                    PokemonNameEntity(3, "poison"),
                )
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }
        }
        val dao = FakeDao()

        val repo = PokemonRepositoryImp(service, dao)

        val resourceFlow = repo.searchPokemonNameByMatch("test").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        assertEquals(false, isNotLoadingResource.isLoading)

        //TODO:
//        assertEquals(true, dao.insert)

        val responseSuccess = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, responseSuccess.count())

        val pokemon = responseSuccess.first() as Resource.Success
        assertEquals(3, pokemon.data!!.count())
    }

    @Test
    fun searchPokemonNameByMatchFailWithNoLocalData(): Unit = runBlocking {
        class FakeService: PokemonService{
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                throw Exception("")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            var insert = false
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                insert = true
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                return emptyList()
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }
        }
        val dao = FakeDao()

        val repo = PokemonRepositoryImp(service, dao)

        val resourceFlow = repo.searchPokemonNameByMatch("test").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        assertEquals(false, isNotLoadingResource.isLoading)

        assertEquals(false, dao.insert)

        val responseError = resourceFlow.filter { it is Resource.Error }
        assertEquals(1, responseError.count())
    }
}