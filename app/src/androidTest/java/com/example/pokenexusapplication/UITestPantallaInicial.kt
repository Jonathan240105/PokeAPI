package com.example.pokenexusapplication

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokenexusapplication.Views.Screens.PantallaInicial
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITestPantallaInicial {

    @get:Rule
    val composeRule = createComposeRule()

    //Función que lanza la pantalla inicial en un contexto composable
    fun lanzarPantallaInicial() {
        composeRule.setContent {
            PantallaInicial()
        }
    }

    //Test que comprueba que el titulo existe
    @Test
    fun `Comprobar_que_el_titulo_existe`() {
        lanzarPantallaInicial()
        composeRule.onNodeWithTag("tituloInicial").assertExists()
    }

    //Test que comprueba que el texto del titulo es lo qe se espera
    @Test
    fun `Comprobar_que_el_texto_del_titulo_es_lo_esperado`() {
        lanzarPantallaInicial()
        composeRule.onNodeWithTag("tituloInicial").assertTextEquals("Poke-Nexus")
    }
}