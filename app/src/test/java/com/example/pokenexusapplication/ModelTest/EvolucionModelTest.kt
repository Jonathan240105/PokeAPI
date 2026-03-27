package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Domain.EvolucionModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class EvolucionModelTest {

    @Test
    fun `Creación de EvolucionModel`() {
        val evolucionModel = EvolucionModel(
            0,
            0,
            "",
            "",
            0
        )
        assertEquals(0, evolucionModel.id)
        assertEquals(0, evolucionModel.idCadena)
        assertEquals("", evolucionModel.nombrePokemonBase)
        assertEquals("", evolucionModel.nombrePokemonEvolucion)
        assertEquals(0, evolucionModel.nivelMinimo)
    }

    @Test
    fun `Actualizar aitributo de EvolucionModel`() {
        val evolucionModel = EvolucionModel(
            id = 1,
            nombrePokemonBase = "Pikachu",
            nombrePokemonEvolucion = "Raichu"
        )

        val evolucionModel2 = evolucionModel.copy(nivelMinimo = 22)

        assertEquals(22, evolucionModel2.nivelMinimo)
        assertEquals("Pikachu", evolucionModel2.nombrePokemonBase)
        assertEquals(1, evolucionModel2.id)
    }

    @Test
    fun `Dos modelos con los mismos datos son iguales`() {
        val modelo1 =
            EvolucionModel(id = 10, nombrePokemonBase = "hola", nombrePokemonEvolucion = "a")
        val modelo2 =
            EvolucionModel(id = 10, nombrePokemonBase = "hola", nombrePokemonEvolucion = "a")

        assertEquals(modelo1, modelo2)
    }
}