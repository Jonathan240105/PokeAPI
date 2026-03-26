package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Domain.Pokemon
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonModelTest {

    @Test
    fun `Creacion de Pokemon `() {
        val pokemon = Pokemon(
            id = 25,
            nombre = "pikachu",
            peso = 60,
            altura = 4
        )

        assertEquals(25, pokemon.id)
        assertEquals("pikachu", pokemon.nombre)
        assertEquals(60, pokemon.peso)
        assertEquals(4, pokemon.altura)
    }

    @Test
    fun `Actualizar un atributo del pokemon`() {
        val pokemon = Pokemon(id = 1, nombre = "Bulbasaur", peso = 69)

        val pokemonModificado = pokemon.copy(nombre = "Ivysaur")

        assertEquals("Ivysaur", pokemonModificado.nombre)
        assertEquals(1, pokemonModificado.id)
        assertEquals(69, pokemonModificado.peso)
    }

    @Test
    fun `Dos objetos Pokemon tienen el mismo valor, por lo tanto son iguales`() {
        val p1 = Pokemon(id = 150, nombre = "Mewtwo")
        val p2 = Pokemon(id = 150, nombre = "Mewtwo")

        assertEquals(p1, p2)
        assertEquals(p1.hashCode(), p2.hashCode())
    }


}