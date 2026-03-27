package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Domain.Descripcion
import com.example.pokenexusapplication.Domain.Idioma
import org.junit.Assert.assertEquals
import org.junit.Test

class DescripcionTest {

    @Test
    fun `Creacion de Descripcion`() {
        val textoPrueba = "Este Pokémon lanza rayos."
        val lenguaje = Idioma("es", "url_es")

        val desc = Descripcion(texto = textoPrueba, idioma = lenguaje)

        assertEquals(textoPrueba, desc.texto)
        assertEquals("es", desc.idioma.nombre)
    }

    @Test
    fun `Actualizar atributos de Descripcion`() {
        val descripcion = Descripcion("Hola", Idioma("es", ""))
        val modificado = descripcion.copy(texto = "Adiós")

        assertEquals("Adiós", modificado.texto)
        assertEquals("es", modificado.idioma.nombre)
    }

    @Test
    fun `Igualdad de objetos Descripcion`() {
        val d1 = Descripcion("Texto", Idioma("en", ""))
        val d2 = Descripcion("Texto", Idioma("en", ""))

        assertEquals(d1, d2)
    }
}