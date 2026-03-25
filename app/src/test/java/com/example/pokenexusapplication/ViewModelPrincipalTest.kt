package com.example.pokenexusapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.Repository.Repository
import com.example.pokenexusapplication.Domain.Pokemon
import com.example.pokenexusapplication.Views.ViewModels.ViewModelPrincipal
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import kotlin.collections.emptyList

class ViewModelPrincipalTest {
    private val repository = mockk<Repository>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcher = Dispatcher()

    @Before
    fun setUp() {
        coEvery { repository.traerPaginaPokemon(any(), any()) } returns emptyList()
        coEvery { repository.listarTodosPokemonsCompactos() } returns flowOf(emptyList())

    }

    @After
    fun limpiarTodo() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Al inicializar, listarPokemons actualiza la lista compacta correctamente`() =runTest{

        val entidadPrueba = PokemonCompactoEntity(1, "bulbasaur", "url/1")

        coEvery { repository.listarTodosPokemonsCompactos() } returns flowOf(listOf(entidadPrueba))

        val viewModel = ViewModelPrincipal(repository)

        val estado = viewModel.model.value
        assertEquals(1, estado.listaPokemonsCompactos.size)
        assertEquals("bulbasaur", estado.listaPokemonsCompactos[0].name)
        assertTrue(estado.succes)
        assertFalse(estado.isLoading)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `cargarSiguientePagina actualiza el estado cuando carga la nueva pagina`() = runTest{

        val pokemonNuevo = mockk<Pokemon>(relaxed = true)
        coEvery { repository.traerPaginaPokemon(20, 0) } returns listOf(pokemonNuevo)

        val viewModel = ViewModelPrincipal(repository)
        viewModel.cargarSiguientePagina()

        assertFalse(viewModel.model.value.isLoading)
    }

    @Test
    fun `Si ya esta cargando, cargarSiguientePagina no hace nada`() {
        val pokemonPrueba = mockk<Pokemon>(relaxed = true)
        coEvery { repository.traerPaginaPokemon(any(), any()) } returns listOf(pokemonPrueba)

        val viewModel = ViewModelPrincipal(repository)

        //Se estaría llamando 3 veces a la función
        viewModel.cargarSiguientePagina()
        viewModel.cargarSiguientePagina()

        //Se supone que de las 3 solo debería llamar 2 porque a la segunda lo bloquea por el isLoading
        coVerify(exactly = 2) { repository.traerPaginaPokemon(any(), any()) }
    }
}

