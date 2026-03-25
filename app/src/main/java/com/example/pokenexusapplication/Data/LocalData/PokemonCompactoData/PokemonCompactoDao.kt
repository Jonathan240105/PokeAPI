package com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokenexusapplication.Data.LocalData.Querys
import com.example.pokenexusapplication.Rutas
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonCompactoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemonsCompactos(pokemonList: List<PokemonCompactoEntity>)

    @Query(Querys.selectAllPokemonCompactoPagina)
    fun getPokemonPagina(limit: Int, offset: Int): List<PokemonCompactoEntity>

    @Query(Querys.selectAllPokemonCompacto)
    fun getAllPokemonsCompactos(): Flow<List<PokemonCompactoEntity>>

    @Query(Querys.numeroDePokemonsCompacto)
    suspend fun getCount(): Int
}