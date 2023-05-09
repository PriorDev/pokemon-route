package com.prior_dev.pokerroutejc.core.components

class SealedMyExceptions(message: String): Exception() {
    companion object{
        const val serverError = "Error al tratar de conectar con el servidor, revisa tu conexion a internet"
    }
}