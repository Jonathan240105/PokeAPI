package com.example.pokenexusapplication

import com.example.pokenexusapplication.Data.LocalData.EspecieData.EspecieDao
import com.example.pokenexusapplication.Data.LocalData.EvolucionData.EvolucionDao
import com.example.pokenexusapplication.Data.LocalData.EvolucionData.EvolucionEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoDao
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonDao
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonEntity
import com.example.pokenexusapplication.Data.RemoteData.DataInterface
import com.example.pokenexusapplication.Data.RemoteData.Responses.CadenaEvolucion
import com.example.pokenexusapplication.Data.RemoteData.Responses.Evolucion
import com.example.pokenexusapplication.Data.RemoteData.Responses.ObjetoCompacto
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonCompactoList
import com.example.pokenexusapplication.Data.Repository.RepositoryImp
import com.example.pokenexusapplication.Domain.Especie
import com.example.pokenexusapplication.Domain.Pokemon
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

class RepositoryTest {
    private val api = mockk<DataInterface>()
    private val pokemonDao = mockk<PokemonDao>()
    private val listDao = mockk<PokemonCompactoDao>()
    private val evoDao = mockk<EvolucionDao>()
    private val especieDao = mockk<EspecieDao>()
    private val repository = RepositoryImp(api, pokemonDao, especieDao, evoDao, listDao)

    //Test que comprueba que si en la base de datos no hay nada de los datos buscados,
    //se llama a la api para que traiga los datos y se actualizan los datos
    @Test
    fun `Si en la base de datos no hay nada, se llama a la api y se actualizan los datos`() =
        runTest {

            coEvery { listDao.getCount() } returns 0
            coEvery { api.listarPokemons() } returns Response.success(PokemonCompactoList(emptyList()))
            coEvery { listDao.insertAllPokemonsCompactos(any()) } returns Unit
            coEvery { listDao.getAllPokemonsCompactos() } returns flowOf(emptyList())

            val result = repository.listarTodosPokemonsCompactos().toList()

            coVerify { api.listarPokemons() }
            coVerify { listDao.insertAllPokemonsCompactos(any()) }
            assertEquals(1, result.size)
        }

    //Test que comprueba que si en la base de datos ya hay datos, no se llama a la api,
    //es decir, utiliza el sistema de cache correctamente
    @Test
    fun `Cuando la base de dats ya tiene datos, listarTodos no llama a la api`() = runTest {
        coEvery { listDao.getCount() } returns 10
        coEvery { listDao.getAllPokemonsCompactos() } returns flowOf(listOf(mockk(), mockk()))

        val result = repository.listarTodosPokemonsCompactos().toList()

        coVerify(exactly = 0) { api.listarPokemons() }
        assertEquals(1, result.size)
    }

    //Test que comprueba si hay datos en la base de datos antes de llamar a la api.
    //Esto evita que se hagan 20 llamadas innecesarias a la vez
    @Test
    fun `traerPaginaPokemon busca en la base de datos antes de llamar a la api`() = runTest {

        val entityCompacta = PokemonCompactoEntity(1, "pikachu", "url")
        val entityCompleta = PokemonEntity(id = 1, nombre = "pikachu")

        coEvery { listDao.getPokemonPagina(any(), any()) } returns listOf(entityCompacta)
        coEvery { pokemonDao.getPokemonByName("pikachu") } returns entityCompleta

        val resultado = repository.getPaginaPokemon(1, 0)

        assertEquals(1, resultado.size)
        assertEquals("pikachu", resultado[0].nombre)
        coVerify(exactly = 0) { api.pillarUnPokemon(any()) }
    }

    //Test que comprueba que si hay datos en la base d edatos sobre las evoluciones, no se llama a la api
    @Test
    fun `Comprobar que si hay evoluciones guardadas en la base de datos, que no se llame a la api`() =
        runTest {
            val datosLocales =
                EvolucionEntity(
                    id = 1,
                    idCadena = 10,
                    nombrePokemonBase = "Pikachu",
                    nombrePokemonEvolucion = "Raichu"

                )
            coEvery { evoDao.getEvoluciones(any()) } returns listOf(datosLocales)

            val resultado = repository.getEvolucion(10).toList()

            assertEquals(1, resultado.first().size)
            assertEquals("Pikachu", resultado.first()[0].nombrePokemonBase)

            coVerify(exactly = 0) { api.getEvolucion(any()) }
        }

    //Test que comprueba que si no hay datos en la base de datos sobre las evoluciones, se llama a la api
    @Test
    fun `Cuando la base de datos esta vacia, la función llama a la api,guarda en la base de datos  y emitie los nuevos`() =
        runTest {
            val idBusqueda = 10

            coEvery { evoDao.getEvoluciones(idBusqueda) } returns emptyList()
            coEvery { evoDao.insertarEvoluciones(any()) } just Runs

            val EvolucionApi = mockk<Evolucion>()
            every { EvolucionApi.id } returns idBusqueda
            every { EvolucionApi.cadenaDeEvolucion } returns CadenaEvolucion(
                pokemon = ObjetoCompacto("pichu", ""),
                evolucion = emptyList(),
                detallesEvolucion = emptyList()
            )
            coEvery { api.getEvolucion(idBusqueda) } returns Response.success(EvolucionApi)

            repository.getEvolucion(idBusqueda).toList()

            coVerify(exactly = 1) { api.getEvolucion(idBusqueda) }
            coVerify(exactly = 1) { evoDao.insertarEvoluciones(any()) }
        }

    //Test que comprueba que no se llama a la api en casso de que exista el pokemon buscado en la base de datos
    @Test
    fun `Al buscar un pokemon por nombre, si esta en la base de datos, devuelve los datos y no llama a la api`() = runTest {
        val nombre = "pikachu"
        val pokemonLocal = PokemonEntity(nombre = nombre, idEspecie = 25)
        coEvery { pokemonDao.getPokemonByName(nombre) } returns pokemonLocal

        val result = repository.getPokemonPorNombre(nombre).first()

        assertEquals(pokemonLocal, result)
        coVerify(exactly = 0) { api.pillarUnPokemon(any()) }
    }

    //Test que comprueba que si no hay datos en la base de datos sobre el pokemon buscado, se llama a la api
    @Test
    fun `Al buscar un pokemon busca en api, guarda en BD y emite si no esta la base de datos`() = runTest {
        val nombre = "bulbasaur"
        val mockPokemonApi = Pokemon(id = 1, nombre = nombre)
        val mockEspecieApi = Especie(id = 1, descripcion = emptyList())

        coEvery { pokemonDao.getPokemonByName(nombre) } returns null
        coEvery { api.pillarUnPokemon(nombre) } returns Response.success(mockPokemonApi)
        coEvery { api.getEspecie(any()) } returns Response.success(mockEspecieApi)

        coEvery { pokemonDao.añadirPokemon(any()) } returns Unit
        coEvery { especieDao.insertarEspecie(any()) } returns Unit

        val result = repository.getPokemonPorNombre(nombre).first()

        assertNotNull(result)
        assertEquals(nombre, result?.nombre)
        coVerify { api.pillarUnPokemon(nombre) }
        coVerify { pokemonDao.añadirPokemon(any()) }
    }

    //Test que comprueba que se llame a la api y guarde la especie en la base de datos
    @Test
    fun `al buscar una especie, llama a la api y guarda en lcoal`() = runTest {
        val id = 1
        val mockEspecie = Especie(id = 1)
        coEvery { especieDao.getEspeciePorId(id) } returns null
        coEvery { api.getEspecie(id) } returns Response.success(mockEspecie)
        coEvery { especieDao.insertarEspecie(any()) } returns Unit

        val result = repository.getEspecie(id).first()

        assertEquals(id, result.id)
        coVerify { especieDao.insertarEspecie(any()) }
    }
}