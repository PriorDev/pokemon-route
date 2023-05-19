package com.prior_dev.pokerroutejc.feature_pokemon.domain

import com.prior_dev.pokerroutejc.core.getBigIdFromPokeUrl
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.VersionGroupDetailResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.VersionGroupResponse
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import java.math.BigInteger

data class MoveData(
    val name: String,
    val id: BigInteger,
    val versionGroupDetails: List<VersionGroupDetailData>,
)

data class VersionGroupDetailData(
    val levelLearnedAt: Int,
    val moveLearnMethodName: String,
    val moveLearnMethodId: BigInteger,
    val versionGroupName: String,
    val versionGroupId: BigInteger,
)

fun MoveResponse.toDomain(): MoveData {
    return MoveData(
        name = move.name,
        id = move.url.getBigIdFromPokeUrl(),
        versionGroupDetails = versionGroupDetails.map {
            it.toDomain()
        }
    )
}

fun VersionGroupDetailResponse.toDomain(): VersionGroupDetailData{
    return VersionGroupDetailData(
        levelLearnedAt = levelLearnedAt,
        moveLearnMethodName= moveLearnMethod.name,
        moveLearnMethodId= moveLearnMethod.url.getBigIdFromPokeUrl(),
        versionGroupName= versionGroup.name,
        versionGroupId= versionGroup.url.getBigIdFromPokeUrl()
    )
}


