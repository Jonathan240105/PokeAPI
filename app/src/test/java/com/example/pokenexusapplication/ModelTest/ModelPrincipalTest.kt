package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Domain.ModelPrincipal
import com.example.pokenexusapplication.Domain.Pokemon
import junit.framework.TestCase
import org.junit.Test

class ModelPrincipalTest {

    @Test
    fun `Creación del modelo inicial`() {
        val modelo = ModelPrincipal(isLoading = true, succes = false)

        TestCase.assertTrue(modelo.isLoading)
        TestCase.assertFalse(modelo.succes)
    }

    @Test
    fun `Actualizar un atributo del modelo`() {
        val model = ModelPrincipal(isLoading = true, succes = false)

        val modelNuevo = model.copy(isLoading = false)

        TestCase.assertFalse(modelNuevo.isLoading)
        TestCase.assertFalse(modelNuevo.succes)
    }

    @Test
    fun `Dos objetos de modelo tienen los mismos valores, por lo tanto son iguales`() {
        val model1 = ModelPrincipal(isLoading = true, succes = false)
        val model2 = ModelPrincipal(isLoading = true, succes = false)

        TestCase.assertEquals(model1, model2)
    }

    @Test
    fun `Al recibir la lista de pokemons, isLoading es false y succes true`() {
        val pokemonEjemplo = Pokemon(nombre = "Pikachu")
        val modeloInicial = ModelPrincipal(isLoading = true, succes = false)

        val modeloCargado = modeloInicial.copy(
            listaPokemons = listOf(pokemonEjemplo),
            isLoading = false,
            succes = true
        )

        TestCase.assertFalse(modeloCargado.isLoading)
        TestCase.assertTrue(modeloCargado.succes)
        TestCase.assertEquals(1, modeloCargado.listaPokemons.size)
        TestCase.assertEquals("Pikachu", modeloCargado.listaPokemons[0].nombre)
    }

    @Test
    fun `El modelo inicial debe tener las listas vacias`() {
        val modelo = ModelPrincipal()

        TestCase.assertTrue(modelo.listaPokemons.isEmpty())
        TestCase.assertTrue(modelo.listaPokemonsCompactos.isEmpty())
    }
}