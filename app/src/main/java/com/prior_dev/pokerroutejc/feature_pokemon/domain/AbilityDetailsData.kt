package com.prior_dev.pokerroutejc.feature_pokemon.domain

import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.AbilityDetailsResponse

data class AbilityDetailsData (
    val name: String = "",
    val shortEffect: String = "",
    val effect: String = "",
)

fun AbilityDetailsResponse.toDomain(): AbilityDetailsData {
    return try{
        val effectInEn = effect_entries.first { it.language.name == "en" }

        AbilityDetailsData(
            name = name.uppercase(),
            shortEffect = effectInEn.short_effect,
            effect = effectInEn.effect
        )
    }catch (e: Exception){
        println("No english version \n ${e.printStackTrace()}")
        AbilityDetailsData()
    }
}
