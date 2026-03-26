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
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import kotlin.collections.emptyList

class RepositoryTest {
    private val api = mockk<DataInterface>()
    private val pokemonDao = mockk<PokemonDao>()
    private val listDao = mockk<PokemonCompactoDao>()
    private val evoDao = mockk<EvolucionDao>()
    private val especieDao = mockk<EspecieDao>()
    private val repository = RepositoryImp(api, pokemonDao, especieDao, evoDao, listDao)

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

    @Test
    fun `Cuando la base de dats ya tiene datos, listarTodos no llama a la api`() = runTest {
        coEvery { listDao.getCount() } returns 10
        coEvery { listDao.getAllPokemonsCompactos() } returns flowOf(listOf(mockk(), mockk()))

        val result = repository.listarTodosPokemonsCompactos().toList()

        coVerify(exactly = 0) { api.listarPokemons() }
        assertEquals(1, result.size)
    }

    @Test
    fun `traerPaginaPokemon busca en DB local antes de llamar a la API`() = runTest {

        val entityCompacta = PokemonCompactoEntity(1, "pikachu", "url")
        val entityCompleta = PokemonEntity(id = 1, nombre = "pikachu")

        coEvery { listDao.getPokemonPagina(any(), any()) } returns listOf(entityCompacta)
        coEvery { pokemonDao.getPokemonByName("pikachu") } returns entityCompleta

        val resultado = repository.traerPaginaPokemon(1, 0)

        assertEquals(1, resultado.size)
        assertEquals("pikachu", resultado[0].nombre)
        coVerify(exactly = 0) { api.pillarUnPokemon(any()) }
    }

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
}