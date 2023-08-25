package com.prior_dev.pokerroutejc.feature_pokemon.data.network.response

data class PastValueResponse(
    val accuracy: Int,
    val effect_chance: Any,
    val effect_entries: List<Any>,
    val power: Any,
    val pp: Any,
    val type: Any,
    val version_group: VersionGroupResponse
)