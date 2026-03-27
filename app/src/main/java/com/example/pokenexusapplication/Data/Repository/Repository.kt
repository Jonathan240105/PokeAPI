package com.example.pokenexusapplication.Data.Repository

import com.example.pokenexusapplication.Data.LocalData.EspecieData.EspecieEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonEntity
import com.example.pokenexusapplication.Domain.EvolucionModel
import com.example.pokenexusapplication.Domain.Pokemon
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun listarTodosPokemonsCompactos(): Flow<List<PokemonCompactoEntity>>
    suspend fun traerPaginaPokemon(limite: Int, salto: Int): List<Pokemon>
    suspend fun getEvolucion(idEvolucion: Int?): Flow<List<EvolucionModel>>
    suspend fun getPokemonPorNombre(nombrePokemon: String): Flow<PokemonEntity?>
    suspend fun getEspecie(idEspecie: Int): Flow<EspecieEntity>
}