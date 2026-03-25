package com.example.pokenexusapplication.Data.Repository

import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.CompactoToEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoDao
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.EntityToPokemon
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonDao
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonToEntity
import com.example.pokenexusapplication.Data.RemoteData.DataInterface
import com.example.pokenexusapplication.Domain.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val api: DataInterface,
    private val dao: PokemonDao,
    private val listDao: PokemonCompactoDao
) : Repository {

    override suspend fun listarTodosPokemonsCompactos(): Flow<List<PokemonCompactoEntity>> = flow {

        val numPokemonCargados = listDao.getCount()

        if (numPokemonCargados == 0) {
            val response = api.listarPokemons()
            if (response.isSuccessful) {
                val pokemons = response.body()
                println(pokemons)
                listDao.insertAllPokemonsCompactos(pokemons?.results?.map {
                    CompactoToEntity(it)
                } ?: emptyList())
                println("Pokemons insertados correctamente")
                emitAll(listDao.getAllPokemonsCompactos())
            }
        } else {
            println("Datos pillados de local")
            emitAll(listDao.getAllPokemonsCompactos())
        }
    }

    override suspend fun traerPaginaPokemon(limite: Int, salto: Int): List<Pokemon> {
        val Pokemonscompactos = listDao.getPokemonPagina(limite, salto)

        val listaFinal = mutableListOf<PokemonEntity>()

        Pokemonscompactos.forEach {
            var PokemonEntero = dao.getPokemonByName(it.name)
            if (PokemonEntero == null) {
                try {
                    val response = api.pillarUnPokemon(it.name)

                    if (response.isSuccessful) {
                        val pokemonTraido = PokemonToEntity(response.body() ?: Pokemon())
                        dao.añadirPokemon(pokemonTraido)
                        PokemonEntero = pokemonTraido
                    }
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
            PokemonEntero?.let { listaFinal.add(it) }
        }
        return listaFinal.map { EntityToPokemon(it) }
    }
}