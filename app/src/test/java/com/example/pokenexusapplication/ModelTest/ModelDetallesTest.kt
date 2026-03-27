package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Domain.EvolucionModel
import com.example.pokenexusapplication.Domain.ModelDetalles
import com.example.pokenexusapplication.Domain.Pokemon
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ModelDetallesTest {

    @Test
    fun `Creacion de ModelDetallesTest`() {
        val model = ModelDetalles()

        assertEquals(Pokemon(), model.pokemonActual)
        assertTrue(model.listaEvoluciones.isEmpty())
    }

    @Test
    fun `Actualizar un atirbuto de model`() {
        val pokemonPrueba = Pokemon(nombre = "Pikachu")
        val model = ModelDetalles(pokemonActual = Pokemon(nombre = "Bulbasaur"))

        val modelCopiado = model.copy(pokemonActual = pokemonPrueba)

        assertEquals("Pikachu", modelCopiado.pokemonActual.nombre)
        assertEquals("Bulbasaur", model.pokemonActual.nombre)
    }

    @Test
    fun `verificar que dos objetos con mismos datos son iguales`() {
        val pokemon = Pokemon(nombre = "Charmander")
        val lista = listOf(
            EvolucionModel(
                nombrePokemonBase = "Charmander",
                nombrePokemonEvolucion = "Charmeleon"
            )
        )

        val model1 = ModelDetalles(pokemon, lista)
        val model2 = ModelDetalles(pokemon, lista)

        assertEquals(model1, model2)
        assertEquals(model1.hashCode(), model2.hashCode())

    }

}