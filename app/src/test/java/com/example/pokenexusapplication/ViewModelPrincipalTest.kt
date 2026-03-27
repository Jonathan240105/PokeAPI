package com.example.pokenexusapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.Repository.Repository
import com.example.pokenexusapplication.Domain.Pokemon
import com.example.pokenexusapplication.Views.ViewModels.ViewModelPrincipal
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.collections.emptyList

class ViewModelPrincipalTest {
    private val repository = mockk<Repository>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcher = Dispatcher()

    @Before
    fun setUp() {
        coEvery { repository.getPaginaPokemon(any(), any()) } returns emptyList()
        coEvery { repository.listarTodosPokemonsCompactos() } returns flowOf(emptyList())

    }

    @After
    fun limpiarTodo() {
        Dispatchers.resetMain()
    }

    //Test que comprueba que al inicializar el viewmodel, se cargan los pokemons compactos,
    //es decir unicamente los nombres para comprobar si su datos se han traido
    @Test
    fun `Al inicializar, listarPokemons actualiza la lista compacta correctamente`() = runTest {

        val entidadPrueba = PokemonCompactoEntity(1, "bulbasaur", "url/1")

        coEvery { repository.listarTodosPokemonsCompactos() } returns flowOf(listOf(entidadPrueba))

        val viewModel = ViewModelPrincipal(repository)

        val estado = viewModel.model.value
        assertEquals(1, estado.listaPokemonsCompactos.size)
        assertEquals("bulbasaur", estado.listaPokemonsCompactos[0].name)
        assertTrue(estado.succes)
        assertFalse(estado.isLoading)
    }

    //Test que comprueba que cuando se ejecuta cargaSiguientePagina y termina,
    //el estado del model se actualiza correctamente
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `cargarSiguientePagina actualiza el estado cuando carga la nueva pagina`() = runTest {

        val pokemonNuevo = mockk<Pokemon>(relaxed = true)
        coEvery { repository.getPaginaPokemon(any(), any()) } returns listOf(pokemonNuevo)

        val viewModel = ViewModelPrincipal(repository)
        viewModel.cargarSiguientePagina()

        advanceUntilIdle()
        assertFalse(viewModel.model.value.isLoading)
    }

    //Test que comprueba que no se ejecute cargarSiguientePagina mientras el model tenga el isloading true
    @Test
    fun `Si ya esta cargando, cargarSiguientePagina no hace nada`() {
        val pokemonPrueba = mockk<Pokemon>(relaxed = true)
        coEvery { repository.getPaginaPokemon(any(), any()) } returns listOf(pokemonPrueba)

        val viewModel = ViewModelPrincipal(repository)

        viewModel.cargarSiguientePagina()
        viewModel.cargarSiguientePagina()

        coVerify(exactly = 2) { repository.getPaginaPokemon(any(), any()) }
    }
}

