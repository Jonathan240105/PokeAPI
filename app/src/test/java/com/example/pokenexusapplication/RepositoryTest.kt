package com.example.pokenexusapplication

import com.example.pokenexusapplication.Data.LocalData.EspecieData.EspecieDao
import com.example.pokenexusapplication.Data.LocalData.EvolucionData.EvolucionDao
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoDao
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonDao
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonEntity
import com.example.pokenexusapplication.Data.RemoteData.DataInterface
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonCompactoList
import com.example.pokenexusapplication.Data.Repository.RepositoryImp
import io.mockk.coEvery
import io.mockk.coVerify
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
}