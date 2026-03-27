package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.RemoteData.Responses.EspecieCompacta
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class EspecieCompactaTest {

    @Test
    fun `Creacion de EspecieCompacta`() {
        val nombreEsperado = "bulbasaur"
        val urlEsperada = "https://pokeapi.co/api/v2/pokemon-species/1/"

        val modelo = EspecieCompacta(nombre = nombreEsperado, url = urlEsperada)

        assertEquals(nombreEsperado, modelo.nombre)
        assertEquals(urlEsperada, modelo.url)
    }

    @Test
    fun `Actualizar un atributo de EspecieCompacta`() {
        val original = EspecieCompacta(nombre = "ivysaur", url = "url_vieja")

        val modificado = original.copy(url = "url_nueva")

        assertEquals("url_nueva", modificado.url)
        assertEquals("ivysaur", modificado.nombre) // El nombre debe persistir
    }

    @Test
    fun `Dos objetos con los mismos datos deben ser iguales`() {
        val especie1 = EspecieCompacta("charizard", "https://pokeapi.co/api/v2/1/")
        val especie2 = EspecieCompacta("charizard", "https://pokeapi.co/api/v2/1/")

        assertEquals(especie1, especie2)
        assertEquals(especie1.hashCode(), especie2.hashCode())
    }

    @Test
    fun `Objetos con nombres distintos no deben ser iguales`() {
        val especie1 = EspecieCompacta("pichu", "url_comun")
        val especie2 = EspecieCompacta("pikachu", "url_comun")

        assertNotEquals(especie1, especie2)
    }
}