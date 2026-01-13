package com.priorDev.pokerroutejc.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TypeEntity::class,
        PokemonNameEntity::class,
        DamageRelationsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MyDataBase : RoomDatabase() {
    abstract val typeDao: TypeDao
    abstract val pokemonDao: PokemonDao

    companion object {
        const val DATABASE_NAME = "pokemonDB"
    }
}
