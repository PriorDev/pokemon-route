package com.prior_dev.pokemonrroutejc.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prior_dev.pokemonrroutejc.feature_pokemon.data.database.PokemonDao
import com.prior_dev.pokemonrroutejc.feature_pokemon.data.database.PokemonNameEntity
import com.prior_dev.pokemonrroutejc.feature_types.data.database.DoubleDamageFromCrossRefEntity
import com.prior_dev.pokemonrroutejc.feature_types.data.database.TypeDao
import com.prior_dev.pokemonrroutejc.feature_types.data.database.TypeEntity

@Database(
    entities = [
        TypeEntity::class,
        PokemonNameEntity::class,
        DoubleDamageFromCrossRefEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MyDataBase: RoomDatabase() {
    abstract val typeDao: TypeDao
    abstract val pokemonDao: PokemonDao

    companion object{
        const val DATABASE_NAME = "pokemonDB"
    }
}