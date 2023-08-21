package com.prior_dev.pokerroutejc.core

data class CommonStates(
    val isLoading: Boolean = true,
    val message: String? = "",
    val searchText: String = "",
)
