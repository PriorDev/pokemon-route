package com.priorDev.pokerroutejc.core.data.network.pokemon

import com.priorDev.pokerroutejc.core.data.network.utils.EndPoints
import com.priorDev.pokerroutejc.core.data.network.NetworkService
import com.priorDev.pokerroutejc.core.data.network.utils.NetworkRequestData
import com.priorDev.pokerroutejc.core.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.core.data.network.pokemon.responses.AbilityDetailsResponse
import com.priorDev.pokerroutejc.core.data.network.pokemon.responses.ContainerPokemonNameResponse
import com.priorDev.pokerroutejc.core.data.network.pokemon.responses.MoveDetailsResponse
import com.priorDev.pokerroutejc.core.data.network.pokemon.responses.PokemonResponse
import io.ktor.util.reflect.typeInfo

class PokemonNetServiceImp(
    private val networkService: NetworkService
) : PokemonNetService {
    override suspend fun getAllPokemons(
        offset: Int,
        limit: Int
    ): NetworkResource<ContainerPokemonNameResponse> {
        val requestData = NetworkRequestData(
            url = EndPoints.POKEMONS,
            params = buildMap {
                put("offset", offset.toString())
                put("limit", limit.toString())
            },
            typeInfo = typeInfo<ContainerPokemonNameResponse>()
        )

        val response = networkService.get<ContainerPokemonNameResponse>(requestData)
        return response
    }

    override suspend fun getPokemon(pokemon: String): NetworkResource<PokemonResponse> {
        val requestData = NetworkRequestData(
            url = EndPoints.POKEMONS,
            segments = listOf(pokemon),
            typeInfo = typeInfo<PokemonResponse>()
        )

        return networkService.get(requestData)
    }

    override suspend fun getMoveDetails(move: String): NetworkResource<MoveDetailsResponse> {
        val requestData = NetworkRequestData(
            url = EndPoints.MOVES,
            segments = listOf(move),
            typeInfo = typeInfo<MoveDetailsResponse>()
        )

        return networkService.get(requestData)
    }

    override suspend fun getAbility(ability: String): NetworkResource<AbilityDetailsResponse> {
        val requestData = NetworkRequestData(
            url = EndPoints.ABILITY,
            segments = listOf(ability),
            typeInfo = typeInfo<AbilityDetailsResponse>()
        )

        return networkService.get(requestData)
    }
}
