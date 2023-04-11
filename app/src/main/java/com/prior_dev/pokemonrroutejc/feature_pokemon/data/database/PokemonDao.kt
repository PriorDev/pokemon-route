package com.prior_dev.pokemonrroutejc.feature_pokemon.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: List<PokemonNameEntity>)

    @Query("Select * From catPokemonName where name like :name")
    suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity>

    @Query("Delete from catPokemonName")
    suspend fun eraseNames()
}