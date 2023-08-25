package com.prior_dev.pokerroutejc.feature_pokemon.data.network.response

data class AbilityDetailsResponse(
    val name: String,
    val effect_entries: List<EffectEntryResponse>,
)