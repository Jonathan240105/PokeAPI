package com.example.pokenexusapplication

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokenexusapplication.Domain.ModelPrincipal
import com.example.pokenexusapplication.Views.Screens.PantallaPrincipal
import com.example.pokenexusapplication.Views.ViewModels.ViewModelPrincipal
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITestPantallaPrincipal {

    @get:Rule

    val composeRule = createComposeRule()

    private val navegar = mockk<() -> Unit>()

    fun lanzarPantallaPrincipal() {
        val viewModel = mockk<ViewModelPrincipal>()
        val navegar = mockk<(String?, Int?) -> Unit>(relaxed = true)
        val estadoInicial = MutableStateFlow(ModelPrincipal(isLoading = true, succes = false))

        every { viewModel.model } returns estadoInicial.asStateFlow()
        composeRule.setContent {
            PantallaPrincipal(viewModel, navegar)
        }
    }

    @Test
    fun `Comprobar_que_el_titulo_existe`() {
        lanzarPantallaPrincipal()
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("tituloPrincipal").assertExists()
    }

    @Test
    fun `Comprobar_que_el_titulo_muestra_lo_esperado`() {
        lanzarPantallaPrincipal()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("tituloPrincipal").assertTextEquals("POKÉDEX")
    }

    @Test
    fun `Comprobar_que_el_TextField_existe`() {
        lanzarPantallaPrincipal()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("textFieldBusqueda").assertExists()
    }

    @Test
    fun `Comprobar_que_el_TextField_muestra_lo_escrito`() {
        lanzarPantallaPrincipal()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("textFieldBusqueda").performTextInput("hola")
        composeRule.onNodeWithTag("textFieldBusqueda").assert(hasText("hola"))
    }
}