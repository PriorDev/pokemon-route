package com.priorDev.pokerroutejc.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: List<PokemonNameEntity>)

    @Query("Select * From pokemonnameentity where name like :name")
    suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity>

    @Query("Delete from pokemonnameentity")
    suspend fun eraseNames()

    @Upsert
    fun upsertAll(pokemons: List<PokemonNameEntity>)

    @Query("Select * From pokemonnameentity")
    fun pagingSource(): PagingSource<Int, PokemonNameEntity>

    @Query(
        """
          Select * 
            From pokemonnameentity
            Where :time - savedTime > :threshold  
        """
    )
    fun getExpiredPokemons(
        threshold: Long,
        time: Long = System.currentTimeMillis()
    ): List<PokemonNameEntity>
}
