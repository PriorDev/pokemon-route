package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import com.google.gson.annotations.SerializedName

data class EffectEntryResponse(
    val effect: String,
    val language: LanguageResponse,
    @SerializedName("short_effect")
    val shortEffect: String
)
