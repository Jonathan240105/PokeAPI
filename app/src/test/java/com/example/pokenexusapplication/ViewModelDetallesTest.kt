package com.example.pokenexusapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokenexusapplication.Data.LocalData.EspecieData.EspecieEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonEntity
import com.example.pokenexusapplication.Data.Repository.Repository
import com.example.pokenexusapplication.Domain.EvolucionModel
import com.example.pokenexusapplication.Views.ViewModels.ViewModelDetalles
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ViewModelDetallesTest {

    private val repository = mockk<Repository>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcher = Dispatcher()

    //Test que comprueba que al buscar un pokemon por su nombre, el model de la pantalla de detalles se actuaiza
    @Test
    fun `Al_buscar_un_pokemon_por_su_nombre_el_model_se_actualiza`() =
        runTest {
            val nombre = "pikachu"
            val entidadMock = PokemonEntity(id = 25, nombre = nombre, idEspecie = 25)
            coEvery { repository.getPokemonPorNombre(nombre) } returns flowOf(entidadMock)

            val viewModel = ViewModelDetalles(repository)

            viewModel.getPokemonPorNombre(nombre)
            advanceUntilIdle()

            val pokemonResultante = viewModel.model.value.pokemonActual
            assertEquals(nombre, pokemonResultante.nombre)
            assertEquals(25, pokemonResultante.id)
            coVerify { repository.getPokemonPorNombre(nombre) }
        }

    //Test que comprueba que al buscar una especie y sus evoluciones, el model se actualiza correctamente
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Al_buscar_un_pokemon_se_actualiza_el_model`() =
        runTest {
            val idEspecie = 1
            val idEvolucionIntermedia = 10
            val especieMock = EspecieEntity( idEspecie,  idEvolucionIntermedia)
            val listaEvolucionesMock = listOf(
                EvolucionModel(nombrePokemonBase = "bulbasaur", nombrePokemonEvolucion = "ivysaur")
            )

            coEvery { repository.getEspecie(idEspecie) } returns flowOf(especieMock)
            coEvery { repository.getEvolucion(idEvolucionIntermedia) } returns flowOf(
                listaEvolucionesMock
            )

            val viewModel = ViewModelDetalles(repository)

            viewModel.getEspecieYEvoluciones(idEspecie)
            advanceUntilIdle()

            val estadoFinal = viewModel.model.value
            assertEquals(1, estadoFinal.listaEvoluciones.size)
            assertEquals("ivysaur", estadoFinal.listaEvoluciones[0].nombrePokemonEvolucion)

            coVerify { repository.getEvolucion(idEvolucionIntermedia) }
        }
}