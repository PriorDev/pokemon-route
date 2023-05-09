package com.prior_dev.pokerroutejc.feature_types.data.network.response

import com.google.gson.annotations.SerializedName

data class ContainerTypeResponse (
    @SerializedName("count") val count: Int,
    @SerializedName("results") val types: List<TypeResponse>
)

data class TypeResponse (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)