package com.example.pokenexusapplication

import com.example.pokenexusapplication.Domain.ModelInicial
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ModelInicialTest {

    @Test
    fun `Creación del modelo inicial`() {
        val modelo = ModelInicial(true, false)

        assertTrue(modelo.isLoading)
        assertFalse(modelo.succes)
    }

    @Test
    fun `Actualizar un atributo del modelo`() {
        val model = ModelInicial(true, false)

        val modelNuevo = model.copy(isLoading = false)

        assertFalse(modelNuevo.isLoading)
        assertFalse(modelNuevo.succes)
    }

    @Test
    fun `Dos objetos de modelo tienen los mismos valores, por lo tanto son iguales`() {
        val model1 = ModelInicial(true, false)
        val model2 = ModelInicial(true, false)

        assertEquals(model1, model2)
    }
}