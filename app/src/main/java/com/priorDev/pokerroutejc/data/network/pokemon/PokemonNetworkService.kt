package com.priorDev.pokerroutejc.data.network.pokemon

import com.priorDev.pokerroutejc.data.network.EndPoints
import com.priorDev.pokerroutejc.data.network.INetWorkService
import com.priorDev.pokerroutejc.data.network.NetworkRequestData
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.featurePokemon.data.IPokemonNetworkService
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.AbilityDetailsResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.ContainerPokemonNameResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.MoveDetailsResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.PokemonResponse
import io.ktor.util.reflect.typeInfo
import javax.inject.Inject

class PokemonNetworkService @Inject constructor(
    private val networkService: INetWorkService
) : IPokemonNetworkService {
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
