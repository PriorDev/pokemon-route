package com.priorDev.pokerroutejc.featurePokemon.domain

import com.priorDev.pokerroutejc.featurePokemon.data.network.response.AbilityDetailsResponse

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
