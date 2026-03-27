package com.example.pokenexusapplication.Data.Repository

import com.example.pokenexusapplication.Data.LocalData.EspecieData.EspecieDao
import com.example.pokenexusapplication.Data.LocalData.EspecieData.EspecieEntity
import com.example.pokenexusapplication.Data.LocalData.EspecieData.EspecieToEntity
import com.example.pokenexusapplication.Data.LocalData.EvolucionData.EntityToEvolucion
import com.example.pokenexusapplication.Data.LocalData.EvolucionData.EvolucionDao
import com.example.pokenexusapplication.Data.LocalData.EvolucionData.EvolucionesToEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.CompactoToEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoDao
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.EntityToPokemon
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonDao
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonToEntity
import com.example.pokenexusapplication.Data.RemoteData.DataInterface
import com.example.pokenexusapplication.Domain.Especie
import com.example.pokenexusapplication.Domain.EvolucionModel
import com.example.pokenexusapplication.Domain.Pokemon
import com.example.pokenexusapplication.Domain.getDescripcionDeLista
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val api: DataInterface,
    private val pokedao: PokemonDao,
    private val especieDao: EspecieDao,
    private val evoDao: EvolucionDao,
    private val listDao: PokemonCompactoDao
) : Repository {

    //Función que busca todos los pokemons compactos en la base de datos, en caso de no encontrarlo, hace la llamada a la api y lo busca.
    //Una vez guardados emite un flow con la lista para que pueda ser estructurada y mostrada en la pantalla
    //El endpoint lista los pokemon compactos, es decir, solo el nombre y la url
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

    //Función que busca un pokemon por su nombre, en caso de no encontrarlo, hace la llamada a la api y lo busca.
    //El endpoint de esta llamada si devuelve el pokemon entero, por lo que utilizando los nombres de la llamada
    // de listado de pokemons compactos, saco el detalle de cualquier pokemon.

    override suspend fun getPaginaPokemon(limite: Int, salto: Int): List<Pokemon> {
        val Pokemonscompactos = listDao.getPokemonPagina(limite, salto)

        val listaFinal = mutableListOf<PokemonEntity>()

        Pokemonscompactos.forEach {
            var PokemonEntero = pokedao.getPokemonByName(it.name)
            if (PokemonEntero == null) {
                try {
                    val response = api.pillarUnPokemon(it.name)

                    if (response.isSuccessful) {

                        val pokemonTraido = PokemonToEntity(response.body() ?: Pokemon())

                        val especie = api.getEspecie(pokemonTraido.idEspecie)
                        if (especie.isSuccessful) {
                            pokemonTraido.descripcion =
                                getDescripcionDeLista(especie.body()?.descripcion)
                            especieDao.insertarEspecie(
                                EspecieToEntity(
                                    especie.body() ?: Especie()
                                )
                            )

                        }
                        pokedao.añadirPokemon(pokemonTraido)
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

    //FUnción que busca las evoluciones de un pokemon en la abse de datos, en caso de no ecnontrarlo, hace la llamada a la api y los guarda en la base de datos.
    //Una vez guardados, emite un flow que lleva la lista de evoluciones
    override suspend fun getEvolucion(idEvolucion: Int?): Flow<List<EvolucionModel>> = flow {
        var evolucionesLocales = evoDao.getEvoluciones(idEvolucion ?: 1)

        if (evolucionesLocales.isEmpty()) {
            try {
                val response = api.getEvolucion(idEvolucion)
                if (response.isSuccessful) {
                    val evolucion = response.body()
                    if (evolucion != null) {
                        var evoluciones = EvolucionesToEntity(evolucion)
                        evoDao.insertarEvoluciones(evoluciones)
                        evolucionesLocales = evoluciones
                    }
                }
            } catch (e: Exception) {
                println("Error algo fue makl: ${e.message}")
            }
        }
        println(evolucionesLocales)
        emit(EntityToEvolucion(evolucionesLocales))
    }

    //Función que busca un pokemon por su nombre en la base de datos,
    //en caso de no encontrarlo, hace la llamada a la api y lo busca.
    // Una vez lo guarda, emite un flow con el pokemon.

    override suspend fun getPokemonPorNombre(nombrePokemon: String): Flow<PokemonEntity?> = flow {
        var PokemonEntero = pokedao.getPokemonByName(nombrePokemon)
        if (PokemonEntero == null) {
            try {
                val response = api.pillarUnPokemon(nombrePokemon)

                if (response.isSuccessful) {

                    val pokemonTraido = PokemonToEntity(response.body() ?: Pokemon())

                    val especie = api.getEspecie(pokemonTraido.idEspecie)
                    if (especie.isSuccessful) {
                        pokemonTraido.descripcion =
                            getDescripcionDeLista(especie.body()?.descripcion)
                        especieDao.insertarEspecie(
                            EspecieToEntity(
                                especie.body() ?: Especie()
                            )
                        )

                    }
                    pokedao.añadirPokemon(pokemonTraido)
                    PokemonEntero = pokemonTraido
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
        emit(PokemonEntero)
    }

    //Función que busca una especie por su id en la base de datos,
    //en caso de no encontrarlo, hace la llamada a la api y lo busca.
    //Una vez lo guarda, emite un flow con la especie.
    override suspend fun getEspecie(idEspecie: Int): Flow<EspecieEntity> = flow {
        var especie = especieDao.getEspeciePorId(idEspecie)
        if (especie == null) {
            val response = api.getEspecie(idEspecie)

            if (response.isSuccessful) {
                val especieTraida = response.body()
                if (especieTraida != null) {
                    especieDao.insertarEspecie(EspecieToEntity(especieTraida))
                    especie = EspecieToEntity(especieTraida)
                }
            }
        }
        emit(especie ?: EspecieEntity(1, 1))
    }
}