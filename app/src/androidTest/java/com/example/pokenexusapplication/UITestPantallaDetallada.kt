package com.example.pokenexusapplication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokenexusapplication.Data.RemoteData.Responses.Tipo
import com.example.pokenexusapplication.Data.RemoteData.Responses.TipoPokemon
import com.example.pokenexusapplication.Domain.EvolucionModel
import com.example.pokenexusapplication.Domain.ModelDetalles
import com.example.pokenexusapplication.Domain.Pokemon
import com.example.pokenexusapplication.Views.Screens.EvolucionesTab
import com.example.pokenexusapplication.Views.Screens.MovimientosTab
import com.example.pokenexusapplication.Views.Screens.PantallaDetallada
import com.example.pokenexusapplication.Views.Screens.tabRowDetalles
import com.example.pokenexusapplication.Views.ViewModels.ViewModelDetalles
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITestPantallaDetallada {

    @get:Rule
    val composeRule = createComposeRule()
    val pokemonFake = Pokemon(0, "Pikachu", listOf(TipoPokemon(1, Tipo("Fuego"))))

    //Función para lanzar la pantalla de detalle
    fun lanzarPantallaDetallada() {
        val viewModel = mockk<ViewModelDetalles>(relaxed = true)
        val estadoPantalla = MutableStateFlow(
            ModelDetalles(pokemonFake)
        )
        every { viewModel.model } returns estadoPantalla.asStateFlow()

        composeRule.setContent {
            PantallaDetallada(viewModel, "Pikachu", 25)
        }

    }

    //Función que comprueba que el titulo de la pantalla existe
    @Test
    fun `Comprobar_que_el_titulo_existe`() {
        lanzarPantallaDetallada()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("tituloDetallada").assertExists()
    }

    //Test que comprueba que al cambiar de pestaña en el tab se myuestra lo correcto
    @Test
    fun `verificar_cambio_de_tabs`() {

        composeRule.setContent {
            tabRowDetalles(
                pokemon = pokemonFake,
                listaEvoluciones = emptyList(),
                onBuscarPokemon = {},
                listaMovimientos = listOf("Impactrueno")
            )
        }
        composeRule.waitForIdle()

        composeRule.onNodeWithText("Descripción").assertIsDisplayed()
        composeRule.onNodeWithText("Evoluciones").performClick()
        composeRule.onNodeWithText("Este Pokémon no tiene evoluciones conocidas.")
            .assertIsDisplayed()
    }

    //Test que comprueba que en caso de que no haya evoluciones se muestre el texto de vacío correctamente
    @Test
    fun `mostrar_mensaje_cuando_no_hay_movimientos`() {
        composeRule.setContent {
            MovimientosTab(movimientos = emptyList())
        }

        composeRule.onNodeWithText("No hay movimientos registrados").assertIsDisplayed()
    }

    //Test
    @Test
    fun `al_pulsar_evolucion_llama_a_buscarPokemon`() {
        var pokemonBuscado = ""
        val evolucionesPrueba = listOf(
            EvolucionModel(
                nombrePokemonBase = "pichu",
                nombrePokemonEvolucion = "pikachu",
                nivelMinimo = 0
            )
        )

        composeRule.setContent {
            EvolucionesTab(
                evolucionesPrueba,
                { nombre -> pokemonBuscado = nombre }
            )
        }

        composeRule.onNodeWithText("Pikachu").performClick()
        assert(pokemonBuscado == "pikachu")
    }
}