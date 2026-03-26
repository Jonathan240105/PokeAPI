package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Domain.Idioma
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class IdiomaTest {

    @Test
    fun `Creacion de idioma `() {
        val idioma = Idioma(nombre = "es", url = "url")

        assertEquals("es", idioma.nombre)
        assertEquals("url", idioma.url)
    }

    @Test
    fun `Actualizar atributos en Lenguaje`() {
        val idioma = Idioma("en", "url1")
        val copia = idioma.copy("fr")

        assertEquals("fr", copia.nombre)
        assertEquals("url1", copia.url)
    }

    @Test
    fun `Dos lenguajes identicos deben ser iguales`() {
        val l1 = Idioma("es", "url")
        val l2 = Idioma("es", "url")

        assertEquals(l1,l2)
    }
}