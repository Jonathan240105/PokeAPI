package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Domain.Pokemon
import junit.framework.TestCase
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

        TestCase.assertEquals(25, pokemon.id)
        TestCase.assertEquals("pikachu", pokemon.nombre)
        TestCase.assertEquals(60, pokemon.peso)
        TestCase.assertEquals(4, pokemon.altura)
    }

    @Test
    fun `Actualizar un atributo del pokemon`() {
        val pokemon = Pokemon(id = 1, nombre = "Bulbasaur", peso = 69)

        val pokemonModificado = pokemon.copy(nombre = "Ivysaur")

        TestCase.assertEquals("Ivysaur", pokemonModificado.nombre)
        TestCase.assertEquals(1, pokemonModificado.id)
        TestCase.assertEquals(69, pokemonModificado.peso)
    }

    @Test
    fun `Dos objetos Pokemon tienen el mismo valor, por lo tanto son iguales`() {
        val p1 = Pokemon(id = 150, nombre = "Mewtwo")
        val p2 = Pokemon(id = 150, nombre = "Mewtwo")

        TestCase.assertEquals(p1, p2)
        TestCase.assertEquals(p1.hashCode(), p2.hashCode())
    }


}