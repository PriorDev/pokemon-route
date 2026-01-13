package com.priorDev.pokerroutejc.core.domain.pokemon.models

import com.priorDev.pokerroutejc.core.getLongIdFromPokeUrl
import com.priorDev.pokerroutejc.core.data.network.pokemon.responses.MoveResponse
import com.priorDev.pokerroutejc.core.data.network.pokemon.responses.VersionGroupDetailResponse

data class MoveData(
    val name: String,
    val id: Long,
    val versionGroupDetails: List<VersionGroupDetailData>,
)

data class VersionGroupDetailData(
    val levelLearnedAt: Int,
    val moveLearnMethodName: String,
    val moveLearnMethodId: Long,
    val versionGroupName: String,
    val versionGroupId: Long,
)

fun MoveResponse.toDomain(): MoveData {
    return MoveData(
        name = move.name,
        id = move.url.getLongIdFromPokeUrl(),
        versionGroupDetails = versionGroupDetails.map {
            it.toDomain()
        }
    )
}

fun VersionGroupDetailResponse.toDomain(): VersionGroupDetailData {
    return VersionGroupDetailData(
        levelLearnedAt = levelLearnedAt,
        moveLearnMethodName = moveLearnMethod.name,
        moveLearnMethodId = moveLearnMethod.url.getLongIdFromPokeUrl(),
        versionGroupName = versionGroup.name,
        versionGroupId = versionGroup.url.getLongIdFromPokeUrl()
    )
}
