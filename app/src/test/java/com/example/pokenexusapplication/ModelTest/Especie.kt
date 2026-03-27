package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Domain.CadenaEvoluciones
import com.example.pokenexusapplication.Domain.Descripcion
import com.example.pokenexusapplication.Domain.Especie
import com.example.pokenexusapplication.Domain.Idioma
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EspecieTest {

    @Test
    fun `Creacion de Especie`() {
        val especie = Especie()

        assertEquals(0, especie.id)
        assertTrue(especie.descripcion.isEmpty())
        assertEquals("", especie.cadenaEvolucion.url)
    }

    @Test
    fun `Actualizar atyributos de  Especie`() {
        val original = Especie(id = 1, cadenaEvolucion = CadenaEvoluciones("url1"))
        val copia = original.copy(id = 2)

        assertEquals(2, copia.id)
        assertEquals("url1", copia.cadenaEvolucion.url)
    }

    @Test
    fun `Dos objetos Especie con los mismos datos son iguales`() {
        val desc = listOf(Descripcion("Test", Idioma("es", "")))
        val e1 = Especie(id = 10, descripcion = desc)
        val e2 = Especie(id = 10, descripcion = desc)

        assertEquals(e1, e2)
    }
}