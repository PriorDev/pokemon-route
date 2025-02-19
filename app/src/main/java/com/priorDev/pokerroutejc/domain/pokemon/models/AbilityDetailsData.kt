package com.priorDev.pokerroutejc.domain.pokemon.models

import com.priorDev.pokerroutejc.data.network.pokemon.responses.AbilityDetailsResponse

data class AbilityDetailsData(
    val name: String = "",
    val shortEffect: String = "",
    val effect: String = "",
)

@Suppress("TooGenericExceptionCaught")
fun AbilityDetailsResponse.toDomain(): AbilityDetailsData {
    return try {
        val effectInEn = effectEntries.first { it.language.name == "en" }

        AbilityDetailsData(
            name = name.uppercase(),
            shortEffect = effectInEn.shortEffect,
            effect = effectInEn.effect
        )
    } catch (e: Exception) {
        println("No english version \n ${e.message}")
        AbilityDetailsData()
    }
}
