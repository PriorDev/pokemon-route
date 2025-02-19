package com.priorDev.pokerroutejc.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonDao
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.featureTypes.data.database.DamageRelationsEntity
import com.priorDev.pokerroutejc.featureTypes.data.database.TypeDao
import com.priorDev.pokerroutejc.featureTypes.data.database.TypeEntity

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
