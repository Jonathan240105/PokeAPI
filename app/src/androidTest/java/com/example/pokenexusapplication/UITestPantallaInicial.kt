package com.example.pokenexusapplication

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokenexusapplication.Views.Screens.PantallaInicial
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITestPantallaInicial {

    @get:Rule
    val composeRule = createComposeRule()

    //Función que lanza la pantalla inicial en un contexto composable
    fun lanzarPantallaInicial() {
        val viewModel = mockk<ViewModelInicial>(relaxed = true)
        val navegar = mockk<() -> Unit>(relaxed = true)
        val estadoInicial = MutableStateFlow(ModelInicial(isLoading = true, succes = false))

        every { viewModel.model } returns estadoInicial.asStateFlow()
        composeRule.setContent {
            PantallaInicial(viewModel, navegar)
        }
    }

    //Test que comprueba que el titulo existe
    @Test
    fun `Comprobar_que_el_titulo_existe`() {
        lanzarPantallaInicial()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("tituloInicial").assertExists()
    }

    //Test que comprueba que el texto del titulo muestra lo que se espera
    @Test
    fun `Comprobar_que_el_texto_del_titulo_es_lo_esperado`() {
        composeRule.waitForIdle()
        lanzarPantallaInicial()
        composeRule.onNodeWithTag("tituloInicial").assertTextEquals("Poke-Nexus")
    }

    @Test
    fun `Comprobar_que_el_texto_inferior_existe`() {
        composeRule.waitForIdle()
        lanzarPantallaInicial()
        composeRule.onNodeWithTag("textoCarga").assertExists()
    }

    //Test que comprueba que el texto de carga inferior muestra lo que se espera
    @Test
    fun `Comprobar_que_el_texto_inferior_muestra_lo_esperado`() {
        composeRule.waitForIdle()
        lanzarPantallaInicial()
        composeRule.onNodeWithTag("textoCarga").assertTextEquals("Cargando...")
    }

    //Test que comprueba que al haberse cargado los datos, se ejecuta la nevagción y se resetea el estado
    @Test
    fun `Comprobar_funcionalidad_navegacion`() {
        val viewModel = mockk<ViewModelInicial>(relaxed = true)
        val navegar = mockk<() -> Unit>(relaxed = true)

        val estado = MutableStateFlow(ModelInicial(isLoading = true, succes = false))

        every { viewModel.model } returns estado.asStateFlow()

        composeRule.setContent {
            PantallaInicial(viewModel, navegar)
        }

        verify(exactly = 0) { navegar() }

        estado.value = ModelInicial(isLoading = false, succes = true)

        composeRule.waitForIdle()

        verify(exactly = 1) { navegar() }

        verify { viewModel.resetearEstadoInicial() }
    }
}