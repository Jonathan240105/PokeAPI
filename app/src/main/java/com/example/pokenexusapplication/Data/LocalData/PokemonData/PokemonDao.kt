package com.example.pokenexusapplication.Data.LocalData.PokemonData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokenexusapplication.Data.LocalData.Querys
import com.example.pokenexusapplication.Domain.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun añadirPokemons(pokemons: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun añadirPokemon(pokemons: PokemonEntity)

    @Update
    suspend fun actualizarPokemon(pokemon: PokemonEntity)

    @Query(Querys.selectUnPokemon)
    suspend fun getPokemonByName(nombrePokemon: String): PokemonEntity?

    @Query(Querys.selectAllPokemon)
    fun getAllPokemons(): Flow<List<PokemonEntity>>
}