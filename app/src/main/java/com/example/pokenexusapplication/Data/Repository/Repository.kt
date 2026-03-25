package com.example.pokenexusapplication.Data.Repository

import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Domain.Pokemon
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun listarTodosPokemonsCompactos(): Flow<List<PokemonCompactoEntity>>
    suspend fun traerPaginaPokemon(limite: Int, salto: Int): List<Pokemon>
}