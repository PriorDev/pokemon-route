package com.prior_dev.pokerroutejc.repositories.pokemon_repo

import com.prior_dev.pokerroutejc.core.MakeNetworkCall
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonRepositoryImp
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonService
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonDao
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonNameEntity
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.OfficialArtWorkResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.OthersSpritesResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.SpritesResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokeRepoGetPokemon {
    @Test
    fun getPokemonSuccess() = runTest {
        class FakeDao: PokemonDao{
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

        class FakeService: PokemonService{
            val pokemonReturn = PokemonResponse(
                id = 1,
                name = "Bulbasour",
                spritesResponse = SpritesResponse(
                    frontDefault = null,
                    frontShiny = null,
                    backShiny = null,
                    others = OthersSpritesResponse(officialArtwork = OfficialArtWorkResponse(null, null))
                ),
                abilities = emptyList(),
                moves = emptyList(),
                stats = emptyList(),
                types = emptyList()
            )

            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                return pokemonReturn
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()
        val testDispatcher = StandardTestDispatcher(testScheduler)

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall(testDispatcher))

        val resourceFlow = repo.getPokemon("").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        Assert.assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        Assert.assertEquals(false, isNotLoadingResource.isLoading)

        val responseSuccess = resourceFlow.filter { it is Resource.Success }
        Assert.assertEquals(1, responseSuccess.count())
    }

    @Test
    fun getPokemonFail() = runTest {
        class FakeDao: PokemonDao{
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

        class FakeService: PokemonService{
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                throw Exception("")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()
        val testDispatcher = StandardTestDispatcher(testScheduler)

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall(testDispatcher))

        val resourceFlow = repo.getPokemon("").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        Assert.assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        Assert.assertEquals(false, isNotLoadingResource.isLoading)

        val responseError = resourceFlow.filter { it is Resource.Error }
        Assert.assertEquals(1, responseError.count())
    }
}